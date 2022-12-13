package com.jeasy.security;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 授权验证
 *
 * @author TaoBangren
 * @version 1.0
 * @since 16/8/29 下午11:56
 */
@Slf4j
public class TransportSecurity {

    private static final String TOKEN = "transport-security-token";

    public static boolean isSecurityTransport(HttpServletRequest request) {
        if (request.getHeader(TOKEN) != null) {
            return true;
        }
        return false;
    }

    public static Object getParameter(HttpServletRequest request, String key) {
        if (isSecurityTransport(request)) {
            return request.getAttribute(key);
        }
        return request.getParameter(key) == null ? request.getAttribute(key) : request.getParameter(key);
    }

    /**
     * 解析url中params放入map中
     *
     * @param queryStr
     * @return
     */
    private static void parseQueryStr(String queryStr, HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        String[] queryArray = queryStr.split("&");

        for (int i = 0; i < queryArray.length; i++) {
            String key = queryArray[i].split("=")[0];
            String value = queryArray[i].split("=")[1];
            request.setAttribute(key, value);

            sb.append(key).append("=").append(value).append(",");
        }
    }

    public static String response(HttpServletRequest request, HttpServletResponse response, String source) {

        if (!isSecurityTransport(request)) {

            return source;
        }

        // TODO 对响应信息加密 @陶明凯

//        try {
//            String newSecretKey = SecureTransportService.getSecretkey();
//            String target = SecureTransportService.encodeContent(source,newSecretKey);
//            String newToken= SecureTransportService.encodeSecretKey(newSecretKey);
//            response.setHeader(TOKEN, newToken);
//
//            log.debug("response secret param : " + target) ;
//            return target;
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ServiceException(HttpStatus.BAD_REQUEST.getStatusCode(), "生成token失败");
//        }
        return source;

    }

    public static void request(HttpServletRequest request) {
        if (!isSecurityTransport(request)) {
            return;
        }

        // TODO 根据Header中token进行参数解密 @陶明凯
//        // 对request请求进行解密
//        String token = request.getHeader(TOKEN);
//        log.info("token:" + token);
//        String secretKey = SecureTransportService.decodeSecretkey(token);
//        log.info("secretKey:"+secretKey);
//        try {
//            if(request.getMethod().equalsIgnoreCase("GET")) {
//
//                String queryString = request.getQueryString();
//                log.debug("request secret param : " + queryString);
//
//                String decodeQueryString = SecureTransportService.decodeContent(queryString, secretKey);
//                if (decodeQueryString != null) {
//                    parseQueryStr(decodeQueryString, request);
//                }
//            }
//            // 如果是post 请求对body进行解密
//            if(request.getMethod().equalsIgnoreCase("POST")) {
//                String body = request.getParameter("body");
//                String userToken = request.getParameter("userToken");
//                String decodeBody = SecureTransportService.decodeContent(body,secretKey);
//                request.setAttribute("body", decodeBody);
//                request.setAttribute("userToken", userToken);
//
//                log.debug("request secret param : body = " + body + ", userToken = " + userToken);
//                log.info("request param : body = " + decodeBody + ", userToken = " + userToken);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ServiceException(HttpStatus.BAD_REQUEST.getStatusCode(), "解析token失败");
//        }
    }
}
