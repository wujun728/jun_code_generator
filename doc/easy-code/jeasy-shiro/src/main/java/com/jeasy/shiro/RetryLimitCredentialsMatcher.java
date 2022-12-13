package com.jeasy.shiro;

import com.jeasy.base.web.dto.ModelResult;
import com.jeasy.common.security.PasswordHash;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.MessageException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 输错5次密码锁定半小时，ehcache.xml配置
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class RetryLimitCredentialsMatcher extends HashedCredentialsMatcher implements InitializingBean {

    private static final String DEFAULT_CACHE_NAME = "retryLimitCache";

    private static final String RETRY_LIMIT_PREFIX = "LOGIN_RETRY_LIMIT";

    private final CacheManager cacheManager;

    private String retryLimitCacheName;

    private Integer retryLimitCount;

    private Cache<String, AtomicInteger> passwordRetryCache;

    private PasswordHash passwordHash;

    public RetryLimitCredentialsMatcher(final CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        this.retryLimitCacheName = DEFAULT_CACHE_NAME;
    }

    @Override
    public boolean doCredentialsMatch(final AuthenticationToken authcToken, final AuthenticationInfo info) {
        String retryLimitKey = RETRY_LIMIT_PREFIX + StrKit.S_COLON + authcToken.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(retryLimitKey);
        if (retryCount == null) {
            retryCount = new AtomicInteger(0);
        }
        if (retryCount.incrementAndGet() > retryLimitCount) {
            //if retry count > retryLimitCount throw
            throw new MessageException(ModelResult.CODE_200, "密码连续输入错误超" + retryLimitCount + "次，锁定半小时！");
        }

        boolean matches = super.doCredentialsMatch(authcToken, info);
        if (matches) {
            //clear retry data
            passwordRetryCache.remove(retryLimitKey);
        } else {
            passwordRetryCache.put(retryLimitKey, retryCount);
        }
        return matches;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(passwordHash, "you must set passwordHash!");
        super.setHashAlgorithmName(passwordHash.getAlgorithmName());
        super.setHashIterations(passwordHash.getHashIterations());
        this.passwordRetryCache = cacheManager.getCache(retryLimitCacheName);
    }
}
