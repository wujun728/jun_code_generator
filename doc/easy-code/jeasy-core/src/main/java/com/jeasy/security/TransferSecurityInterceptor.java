package com.jeasy.security;

import com.google.common.collect.Sets;
import com.jeasy.base.web.dto.CurrentUser;
import com.jeasy.base.web.dto.Device;
import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.Func;
import com.jeasy.common.json.JsonKit;
import com.jeasy.common.net.IpKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.thread.ThreadLocalKit;
import com.jeasy.common.web.RequestKit;
import com.jeasy.exception.MessageException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

/**
 * 授权验证拦截器
 *
 * @author taomk
 * @version 1.0
 * @since 15-5-22 下午7:57
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class TransferSecurityInterceptor extends HandlerInterceptorAdapter {

    private static final Set<String> ALLOW_REFERER_DOMAINS = Sets.newHashSet("http://127.0.0.1", "http://localhost");
    public static final String STR_USER = "user";
    public static final String STR_USER_ID = "userId";
    public static final String STR_USER_NAME = "userName";

    private boolean checkReferer = true;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        checkRefererAllow(request);

        ThreadLocalKit.putTime(System.currentTimeMillis());
        // 请求解密
        TransportSecurity.request(request);
        return transferRequest(request, handler);
    }

    private void checkRefererAllow(HttpServletRequest request) {
        boolean isNotAllow = true;
        String referer = RequestKit.getRefererUrl(request);
        if (checkReferer) {
            for (String allowDomain : ALLOW_REFERER_DOMAINS) {
                if (!Func.isEmpty(referer) && referer.trim().startsWith(allowDomain)) {
                    isNotAllow = false;
                    break;
                }
            }
        } else {
            isNotAllow = false;
        }

        if (!Func.isEmpty(referer) && isNotAllow) {
            throw new MessageException(ModelResult.CODE_401, "http referer is not allow");
        }
    }

    private boolean transferRequest(HttpServletRequest request, Object handler) throws Exception {
        // Header头部取device信息
        Device device = new Device();
        device.setVersion(request.getHeader("version"));
        device.setDevice(request.getHeader("device"));
        device.setPlatform(request.getHeader("platform"));
        device.setDeviceModel(request.getHeader("deviceModel"));
        device.setOsVersion(request.getHeader("osVersion"));
        device.setUserAgent(request.getHeader("User-Agent"));
        device.setReferer(request.getHeader("Referer"));

        request.setAttribute("device", device);
        ThreadLocalKit.putDevice(device);

        CurrentUser user;

        // 请求来源于PC
        if (request.getParameterMap().containsKey(STR_USER)) {
            // 内部应用调用 必须传入当前用户
            user = JsonKit.fromJson(request.getParameter(STR_USER), CurrentUser.class);
        } else {
            user = (CurrentUser) SecurityUtils.getSubject().getPrincipal();
        }

        // 请求body转换
        Object body = TransportSecurity.getParameter(request, "body");
        if (body != null) {
            Map<String, Object> params = JsonKit.fromJson(body.toString(), JsonKit.MAP_OBJ_TYPE_TOKEN.getType());
            boolean isFromAPP = StrKit.isNotBlank(device.getPlatform()) && StrKit.equalsIgnoreCase("APP", device.getPlatform());
            if (isFromAPP) {
                // APP获取设备码
                device.setDeviceNo(request.getHeader("deviceNo"));
                // 请求来源于APP/H5/WX
                Method targetMethod = ((HandlerMethod) handler).getMethod();
                HttpAuth httpAuth = targetMethod.getAnnotation(HttpAuth.class);
                boolean isRequireAuth = httpAuth != null && httpAuth.isRequireAuth();

                if (isRequireAuth) {
                    user = checkUserValid(request, httpAuth);
                } else if (params.containsKey(STR_USER_ID) && params.containsKey(STR_USER_NAME)) {
                    user.setId(Long.valueOf(String.valueOf(params.get(STR_USER_ID))));
                    user.setName(String.valueOf(params.get(STR_USER_NAME)));
                }
            } else {
                // PC获取IP
                device.setDeviceNo(IpKit.getRealIP(request));
            }
        }

        ThreadLocalKit.putCurrentUser(user);
        return true;
    }

    public CurrentUser checkUserValid(HttpServletRequest request, HttpAuth httpAuth) {
        // TODO 校验User token是否有效
//        String token = (String)TRANSPORT_SECURITY.getParameter(request, "userToken");
//
//        if(StrKit.isBlank(token)) {
//            log.warn("token is null");
//            throw new ServiceException(ModelResult.CODE_401, "token is null");
//        }
//
//        AuthRequest authRequest = new AuthRequest(token);
//        AuthResponse authResponse;
//
//        try {
//            authResponse = AuthService.auth(authRequest);
//        } catch (AuthException e) {
//            log.warn("token authorize fail : " + token);
//            throw new ServiceException(ModelResult.CODE_406, "token authorize fail");
//        }
//
//        if(authResponse.getIs_expire()) {
//            if(httpAuth.isNeedReLoginWhenExpire()) {
//                log.warn("token expired : " + token);
//                throw new ServiceException(ModelResult.CODE_406, "token expired");
//            }
//        }
//
//        Object data = authResponse.getAttribute("data");
//        return JSON.parseObject(data.toString(), UserToken.class);
        return null;
    }
}
