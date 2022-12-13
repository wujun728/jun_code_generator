package com.jeasy.common.web;

import com.jeasy.common.captcha.CaptchaMaker;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.str.StrKit;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class RequestKit extends org.springframework.web.util.WebUtils {

    /**
     * 前端用户
     */
    public static final String FRONT_USER_TAG = "front_user";
    /**
     * 后端用户
     */
    public static final String USER_TAG = "user";
    /**
     * 后端用户权限tag
     */
    public static final String USER_PERMISSION_TAG = "permission";
    /**
     * 验证码session key
     */
    private static final String CAPTCHA_TAG = "CAPTCHA_TAG";

    /**
     * get 前端用户
     */
    public static <T> T getFrontUser() {
        return (T) getSession().getAttribute(FRONT_USER_TAG);
    }

    /**
     * set 前端用户
     */
    public static void setFrontUser(final HttpServletRequest request, final Object object) {
        getSession().setAttribute(FRONT_USER_TAG, object);
    }

    /**
     * 发送给客户端验证码
     *
     * @param response
     * @throws Exception
     */
    public static void sendCaptcha(final HttpServletResponse response) {
        CaptchaMaker.init(response).start();
    }

    /**
     * 验正验证码
     *
     * @param clientString
     * @return
     */
    public static boolean validCaptcha(final HttpServletRequest request, final HttpServletResponse response, final String clientString) {
        return CaptchaMaker.validate(request, response, clientString);
    }

    /**
     * get current user
     */
    public static <T> T getCurrentUser(final HttpServletRequest request) {
        return (T) getSession().getAttribute(USER_TAG);
    }

    /**
     * set current user
     */
    public static void setCurrentUser(final HttpServletRequest request, final Object object) {
        getSession().setAttribute(USER_TAG, object);
    }

    /**
     * 获取权限
     *
     * @param request
     * @return
     */
    public static <T> T getUserPermission(final HttpServletRequest request) {
        return (T) getSession().getAttribute(USER_PERMISSION_TAG);
    }

    /**
     * 设置权限
     *
     * @param request
     * @param object
     */
    public static void setUserPermission(final HttpServletRequest request, final Object object) {
        getSession().setAttribute(USER_PERMISSION_TAG, object);
    }

    /**
     * 获取 包装防Xss Sql注入的 HttpServletRequest
     *
     * @return request
     */
    public static HttpServletRequest wrapSafeRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return new WafRequestWrapper(request);
    }

    /**
     * 判断是否ajax请求
     * spring ajax 返回含有 ResponseBody 或者 RestController注解
     *
     * @param handlerMethod HandlerMethod
     * @return 是否ajax请求
     */
    public static boolean isAjax(final HandlerMethod handlerMethod) {
        ResponseBody responseBody = handlerMethod.getMethodAnnotation(ResponseBody.class);
        if (null != responseBody) {
            return true;
        }
        RestController restAnnotation = handlerMethod.getBeanType().getAnnotation(RestController.class);
        if (null != restAnnotation) {
            return true;
        }
        return false;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(header);
    }

    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取session
     *
     * @return
     */
    public static HttpSession getSession() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    /**
     * 获取application
     *
     * @return
     */
    public static ServletContext getApplication() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getServletContext();
    }

    /**
     * 获取ServletContext
     *
     * @return
     */
    public static ServletContext getServletContext() {
        return ContextLoader.getCurrentWebApplicationContext().getServletContext();
    }


    public static String getString(final String name) {
        return getString(name, null);
    }

    public static String getString(final String name, final String defaultValue) {
        String resultStr = getRequest().getParameter(name);
        if (resultStr == null || StrKit.S_EMPTY.equals(resultStr) || StrKit.S_NULL.equals(resultStr) || StrKit.S_UNDEFINED.equals(resultStr)) {
            return defaultValue;
        } else {
            return resultStr;
        }
    }

    public static int getInt(final String name) {
        return getInt(name, 0);
    }

    public static int getInt(final String name, final int defaultValue) {
        String resultStr = getRequest().getParameter(name);
        if (resultStr != null) {
            try {
                return Integer.parseInt(resultStr);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    public static BigDecimal getBigDecimal(final String name) {
        return getBigDecimal(name, null);
    }

    public static BigDecimal getBigDecimal(final String name, final BigDecimal defaultValue) {
        String resultStr = getRequest().getParameter(name);
        if (resultStr != null) {
            try {
                return BigDecimal.valueOf(Double.parseDouble(resultStr));
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * 根据参数名从HttpRequest中获取String类型的参数值，无值则返回StrKit.S_EMPTY .
     *
     * @param key .
     * @return String .
     */
    public static String getStringUrlDecodeUTF8(final String key) {
        try {
            return URLDecoder.decode(getString(key), CharsetKit.DEFAULT_ENCODE);
        } catch (Exception e) {
            return StrKit.S_EMPTY;
        }

    }

    public static String getStringUrlDecodeGBK(final String key) {
        try {
            return new String(getString(key).getBytes(CharsetKit.GBK), CharsetKit.UTF_8);
        } catch (Exception e) {
            return StrKit.S_EMPTY;
        }

    }

    /**
     * 获取refererUrl
     */
    public static String getRefererUrl(final HttpServletRequest request) {
        return request.getHeader("referer");
    }

    public static String readRequest(final HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }
        } finally {
            request.getReader().close();
        }
        return sb.toString();
    }

    /**
     * 获取user-agent
     */
    public static String getUserAgent(final HttpServletRequest request) {
        return request.getHeader("User-Agent");
    }

    /**
     * 判断客户端浏览器是否为IE
     */
    public static boolean isIE(final HttpServletRequest request) {
        String userAgent = RequestKit.getUserAgent(request);
        return StrKit.containsIgnoreCase(userAgent, "msie");
    }

    public static boolean isGET(final HttpServletRequest request) {
        return StrKit.equalsIgnoreCase("GET", request.getMethod());
    }

    public static boolean isPOST(final HttpServletRequest request) {
        return StrKit.equalsIgnoreCase("POST", request.getMethod());
    }
}
