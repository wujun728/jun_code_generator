package com.jeasy.shiro.service;

import com.jeasy.common.captcha.CaptchaKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.web.CookieKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码缓存
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
@Data
public class CaptchaCacheService implements InitializingBean {

    private static final String DEFAULT_COOKIE_NAME = "cc";

    private static final String DEFAULT_CACHE_NAME = "cache-captcha";

    /**
     * Cookie超时默认为session会话状态
     */
    private static final int DEFAULT_MAX_AGE = -1;
    public static final String COMMON_CAPTCHA = "0000";

    private CacheManager cacheManager;

    private String cacheName;

    private String cookieName;

    private Cache<String, String> captchaCache;

    public CaptchaCacheService() {
        this.cacheName = DEFAULT_CACHE_NAME;
        this.cookieName = DEFAULT_COOKIE_NAME;
    }

    public CaptchaCacheService(CacheManager cacheManager) {
        this();
        this.cacheManager = cacheManager;
    }

    /**
     * 生成验证码
     */
    public void generate(HttpServletRequest request, HttpServletResponse response) {
        // 先检查cookie的uuid是否存在
        String cookieValue = CookieKit.getCookie(cookieName, request);
        boolean hasCookie = true;
        if (StrKit.isBlank(cookieValue)) {
            hasCookie = false;
            cookieValue = StrKit.getUUID();
        }
        String captchaCode = CaptchaKit.generateCode().toUpperCase();
        // 不存在cookie时设置cookie
        if (!hasCookie) {
            CookieKit.setCookie(response, cookieName, cookieValue, DEFAULT_MAX_AGE);
        }
        // 生成验证码
        CaptchaKit.generate(response, captchaCode);
        captchaCache.put(cookieValue, captchaCode);
    }

    /**
     * 仅能验证一次，验证后立即删除
     *
     * @param request          HttpServletRequest
     * @param response         HttpServletResponse
     * @param userInputCaptcha 用户输入的验证码
     * @return 验证通过返回 true, 否则返回 false
     */
    public boolean validate(HttpServletRequest request, HttpServletResponse response, String userInputCaptcha) {
        String cookieValue = CookieKit.getCookie(cookieName, request);
        if (StrKit.isBlank(cookieValue)) {
            return false;
        }
        String captchaCode = captchaCache.get(cookieValue);
        if (StrKit.isBlank(captchaCode)) {
            return false;
        }

        // 测试方便,正式上线,记得注掉 start
        if (StrKit.equalsIgnoreCase(COMMON_CAPTCHA, userInputCaptcha)) {
            captchaCache.remove(cookieValue);
            CookieKit.removeCookie(cookieName, response);
            return true;
        }
        // end

        // 转成大写重要
        userInputCaptcha = userInputCaptcha.toUpperCase();
        boolean result = userInputCaptcha.equals(captchaCode);
        if (result) {
            captchaCache.remove(cookieValue);
            CookieKit.removeCookie(cookieName, response);
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cacheManager, "cacheManager must not be null!");
        Assert.hasText(cacheName, "cacheName must not be empty!");
        Assert.hasText(cookieName, "cookieName must not be empty!");
        this.captchaCache = cacheManager.getCache(cacheName);
    }
}
