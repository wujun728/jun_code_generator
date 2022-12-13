package com.jeasy.shiro.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;

/**
 * 使用spring-cache作为shiro缓存
 * 缓存管理器
 *
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
public class ShiroSpringCacheManager implements CacheManager, Destroyable {

    private org.springframework.cache.CacheManager cacheManager;

    public org.springframework.cache.CacheManager getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(org.springframework.cache.CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (log.isTraceEnabled()) {
            log.trace("Acquiring ShiroSpringCache instance named [" + name + "]");
        }
        org.springframework.cache.Cache cache = cacheManager.getCache(name);
        return new ShiroSpringCache<>(cache);
    }

    @Override
    public void destroy() throws Exception {
        cacheManager = null;
    }
}
