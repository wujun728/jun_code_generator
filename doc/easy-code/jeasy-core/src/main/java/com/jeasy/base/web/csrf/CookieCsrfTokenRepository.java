package com.jeasy.base.web.csrf;

import com.jeasy.common.security.Md5Kit;
import com.jeasy.common.str.StrKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * A {@link CsrfTokenRepository} that persists the CSRF token in a cookie named
 * "XSRF-TOKEN" and reads from the header "X-XSRF-TOKEN" following the conventions of
 * AngularJS. When using with AngularJS be sure to use {@link #withHttpOnlyFalse()}.
 *
 * @author Rob Winch
 * @since 4.1
 */
@Slf4j
@Data
public final class CookieCsrfTokenRepository implements CsrfTokenRepository, InitializingBean {

    static final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";

    static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";

    static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";

    static final String DEFAULT_CSRF_CACHE_NAME = "CSRF_CACHE_NAME";

    private String parameterName = DEFAULT_CSRF_PARAMETER_NAME;

    private String headerName = DEFAULT_CSRF_HEADER_NAME;

    private String cookieName = DEFAULT_CSRF_COOKIE_NAME;

    private String csrfTokenCacheName = DEFAULT_CSRF_CACHE_NAME;

    private final Method setHttpOnlyMethod;

    private boolean cookieHttpOnly;

    private String cookiePath;

    private CacheManager cacheManager;

    private Cache<String, String> csrfTokenCache;

    public CookieCsrfTokenRepository() {
        this.setHttpOnlyMethod = ReflectionUtils.findMethod(Cookie.class, "setHttpOnly", boolean.class);
        if (this.setHttpOnlyMethod != null) {
            this.cookieHttpOnly = true;
        }
    }

    @Override
    public CsrfTokenBean generateToken(final HttpServletRequest request) {
        return new CsrfTokenBean(this.headerName, this.parameterName, createNewToken());
    }

    @Override
    public void saveToken(final CsrfTokenBean token, final HttpServletRequest request, final HttpServletResponse response) {
        String tokenValue = token == null ? StrKit.S_EMPTY : token.getToken();
        Cookie cookie = new Cookie(this.cookieName, tokenValue);
        cookie.setSecure(request.isSecure());
        if (this.cookiePath != null && !this.cookiePath.isEmpty()) {
            cookie.setPath(this.cookiePath);
        } else {
            cookie.setPath(this.getRequestContext(request));
        }
        if (token == null) {
            cookie.setMaxAge(0);
        } else {
            cookie.setMaxAge(-1);
        }
        if (cookieHttpOnly && setHttpOnlyMethod != null) {
            ReflectionUtils.invokeMethod(setHttpOnlyMethod, cookie, Boolean.TRUE);
        }

        response.addCookie(cookie);

        // 写入缓存
        csrfTokenCache.put(getTokenCacheKey(token), tokenValue);
    }

    @Override
    public CsrfTokenBean loadToken(final HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, this.cookieName);
        if (cookie == null) {
            return null;
        }
        String token = cookie.getValue();
        if (!StrKit.hasLength(token)) {
            return null;
        }
        return new CsrfTokenBean(this.headerName, this.parameterName, token);
    }

    @Override
    public CsrfTokenBean loadTokenFromCache(final CsrfTokenBean token) {
        String csrfTokenCacheKey = getTokenCacheKey(token);
        String csrfTokenCacheVal = csrfTokenCache.get(csrfTokenCacheKey);
        if (!StrKit.isBlank(csrfTokenCacheVal)) {
            csrfTokenCache.remove(csrfTokenCacheKey);
            return new CsrfTokenBean(this.headerName, this.parameterName, csrfTokenCacheVal);
        }
        return null;
    }

    private String getTokenCacheKey(final CsrfTokenBean token) {
        return Md5Kit.md5Hex(this.headerName + StrKit.S_UNDERLINE + this.parameterName + StrKit.S_UNDERLINE + (token == null ? StrKit.S_EMPTY : token.getToken()));
    }

    /**
     * Sets the name of the HTTP request parameter that should be used to provide a token.
     *
     * @param parameterName the name of the HTTP request parameter that should be used to
     *                      provide a token
     */
    public void setParameterName(final String parameterName) {
        Assert.notNull(parameterName, "parameterName is not null");
        this.parameterName = parameterName;
    }

    /**
     * Sets the name of the HTTP header that should be used to provide the token.
     *
     * @param headerName the name of the HTTP header that should be used to provide the
     *                   token
     */
    public void setHeaderName(final String headerName) {
        Assert.notNull(headerName, "headerName is not null");
        this.headerName = headerName;
    }

    /**
     * Sets the name of the cookie that the expected CSRF token is saved to and read from.
     *
     * @param cookieName the name of the cookie that the expected CSRF token is saved to
     *                   and read from
     */
    public void setCookieName(final String cookieName) {
        Assert.notNull(cookieName, "cookieName is not null");
        this.cookieName = cookieName;
    }

    /**
     * Sets the HttpOnly attribute on the cookie containing the CSRF token.
     * The cookie will only be marked as HttpOnly if both <code>cookieHttpOnly</code> is <code>true</code> and the underlying version of Servlet is 3.0 or greater.
     * Defaults to <code>true</code> if the underlying version of Servlet is 3.0 or greater.
     * NOTE: The {@link Cookie#setHttpOnly(boolean)} was introduced in Servlet 3.0.
     *
     * @param cookieHttpOnly <code>true</code> sets the HttpOnly attribute, <code>false</code> does not set it (depending on Servlet version)
     * @throws IllegalArgumentException if <code>cookieHttpOnly</code> is <code>true</code> and the underlying version of Servlet is less than 3.0
     */
    public void setCookieHttpOnly(final boolean cookieHttpOnly) {
        if (cookieHttpOnly && setHttpOnlyMethod == null) {
            throw new IllegalArgumentException("Cookie will not be marked as HttpOnly because you are using a version of Servlet less than 3.0. NOTE: The Cookie#setHttpOnly(boolean) was introduced in Servlet 3.0.");
        }
        this.cookieHttpOnly = cookieHttpOnly;
    }

    private String getRequestContext(final HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

    /**
     * Factory method to conveniently create an instance that has
     * {@link #setCookieHttpOnly(boolean)} set to false.
     *
     * @return an instance of CookieCsrfTokenRepository with
     * {@link #setCookieHttpOnly(boolean)} set to false
     */
    public static CookieCsrfTokenRepository withHttpOnlyFalse() {
        CookieCsrfTokenRepository result = new CookieCsrfTokenRepository();
        result.setCookieHttpOnly(false);
        return result;
    }

    private String createNewToken() {
        return UUID.randomUUID().toString();
    }

    /**
     * Set the path that the Cookie will be created with. This will override the default functionality which uses the
     * request context as the path.
     *
     * @param path the path to use
     */
    public void setCookiePath(final String path) {
        this.cookiePath = path;
    }

    /**
     * Get the path that the CSRF cookie will be set to.
     *
     * @return the path to be used.
     */
    public String getCookiePath() {
        return this.cookiePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cacheManager, "you must set cacheManager!");
        this.csrfTokenCache = cacheManager.getCache(csrfTokenCacheName);
    }
}
