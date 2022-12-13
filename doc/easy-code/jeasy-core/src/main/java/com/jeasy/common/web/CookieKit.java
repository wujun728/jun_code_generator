package com.jeasy.common.web;

import org.springframework.util.ReflectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class CookieKit {

    /**
     * Get cookie value by cookie name.
     */
    public static String getCookie(String name, HttpServletRequest request) {
        return getCookie(name, null, request);
    }

    /**
     * Get cookie value by cookie name.
     */
    public static String getCookie(String name, String defaultValue, HttpServletRequest request) {
        Cookie cookie = getCookieObject(name, request);
        return cookie != null ? cookie.getValue() : defaultValue;
    }

    /**
     * Get cookie object by cookie name.
     */
    public static Cookie getCookieObject(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    /**
     * Remove Cookie.
     */
    public static void removeCookie(String name, HttpServletResponse response) {
        doSetCookie(name, null, 0, null, null, null, response);
    }

    /**
     * Remove Cookie.
     */
    public static void removeCookie(String name, String path, HttpServletResponse response) {
        doSetCookie(name, null, 0, path, null, null, response);
    }

    /**
     * Remove Cookie.
     */
    public static void removeCookie(String name, String path, String domain, HttpServletResponse response) {
        doSetCookie(name, null, 0, path, domain, null, response);
    }

    private static void doSetCookie(String name, String value, int maxAgeInSeconds, String path, String domain, Boolean isHttpOnly, HttpServletResponse response) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAgeInSeconds);
        // set the default path value to "/"
        if (path == null) {
            path = "/";
        }
        cookie.setPath(path);

        if (domain != null) {
            cookie.setDomain(domain);
        }
        if (isHttpOnly != null) {
            Method setHttpOnlyMethod = ReflectionUtils.findMethod(Cookie.class, "setHttpOnly", boolean.class);
            if (setHttpOnlyMethod != null) {
                ReflectionUtils.invokeMethod(setHttpOnlyMethod, cookie, Boolean.TRUE);
            }
        }
        response.addCookie(cookie);
    }

    /**
     * 设置cookie
     *
     * @param response
     * @param name
     * @param value
     * @param maxAgeInSeconds
     */
    public static void setCookie(HttpServletResponse response, String name, String value, int maxAgeInSeconds) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAgeInSeconds);
        Method setHttpOnlyMethod = ReflectionUtils.findMethod(Cookie.class, "setHttpOnly", boolean.class);
        if (setHttpOnlyMethod != null) {
            ReflectionUtils.invokeMethod(setHttpOnlyMethod, cookie, Boolean.TRUE);
        }
        response.addCookie(cookie);
    }

}
