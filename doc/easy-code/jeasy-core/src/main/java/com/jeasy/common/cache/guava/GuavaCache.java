package com.jeasy.common.cache.guava;

import com.jeasy.common.str.StrKit;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 基于spring的 guava cache实现（本地缓存）
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class GuavaCache implements Cache, InitializingBean {

    private static final Object NULL_HOLDER = new NullHolder();
    public static final int NUM_50 = 50;

    /**
     * 缓存名称
     */
    private String name;
    /**
     * 缓存数据超时时间，默认最长缓存时间为1小时
     */
    private int expiredDuration = 60 * 60;
    /**
     * 最大缓存数量
     */
    private Integer maximumSize;
    /**
     * guava cache客户端
     */
    private com.google.common.cache.Cache<Object, Object> guavaCacheClient;

    private boolean allowNullValues = true;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.guavaCacheClient == null) {
            CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
            if (this.maximumSize != null) {
                // 最多缓存数量
                cacheBuilder = cacheBuilder.maximumSize(this.maximumSize);
            }
            // // 自首次写入后，默认缓存1小时
            cacheBuilder = cacheBuilder.expireAfterWrite(expiredDuration, TimeUnit.SECONDS);
            this.guavaCacheClient = cacheBuilder.build();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public com.google.common.cache.Cache<Object, Object> getNativeCache() {
        return this.guavaCacheClient;
    }

    @Override
    public ValueWrapper get(final Object key) {
        String cacheKey = getCacheKey(key);
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Object value = guavaCacheClient.getIfPresent(cacheKey);
            sw.stop();
            if (sw.getTime() > NUM_50) {
                log.info(StrKit.format("读取guava cache用时{}, key={}, cacheName={}", sw.getTime(), cacheKey, this.getName()));
            }
            return (value != null ? new SimpleValueWrapper(fromStoreValue(value)) : null);
        } catch (Exception e) {
            log.error(StrKit.format("读取guava cache缓存发生异常, key={}, cacheName={}", cacheKey, this.getName()), e.getCause());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Object key, final Class<T> type) {
        String cacheKey = getCacheKey(key);
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Object value = fromStoreValue(this.guavaCacheClient.getIfPresent(cacheKey));
            if (value != null && type != null && !type.isInstance(value)) {
                throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
            }
            sw.stop();
            if (sw.getTime() > NUM_50) {
                log.info(StrKit.format("读取guava cache用时{}, key={}, cacheName={}", sw.getTime(), cacheKey, this.getName()));
            }
            return (T) value;
        } catch (Exception e) {
            log.error(StrKit.format("读取guava cache缓存发生异常, key={}, cacheName={}", cacheKey, this.getName()), e.getCause());
            return null;
        }
    }

    @Override
    public <T> T get(final Object key, final Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(final Object key, final Object value) {
        this.guavaCacheClient.put(getCacheKey(key), toStoreValue(value));
    }

    @Override
    public ValueWrapper putIfAbsent(final Object key, final Object value) {
        return null;
    }

    @Override
    public void evict(final Object key) {
        this.guavaCacheClient.invalidate(getCacheKey(key));
    }

    @Override
    public void clear() {
        this.guavaCacheClient.invalidateAll();
    }

    /**
     * Convert the given value from the internal store to a user value returned
     * from the get method (adapting {@code null}).
     *
     * @param storeValue the store value
     * @return the value to return to the user
     */
    protected Object fromStoreValue(final Object storeValue) {
        if (this.allowNullValues && storeValue == NULL_HOLDER) {
            return null;
        }
        return storeValue;
    }

    /**
     * Convert the given user value, as passed into the put method, to a value
     * in the internal store (adapting {@code null}).
     *
     * @param userValue the given user value
     * @return the value to store
     */
    protected Object toStoreValue(final Object userValue) {
        if (this.allowNullValues && userValue == null) {
            return NULL_HOLDER;
        }
        return userValue;
    }

    @SuppressWarnings("serial")
    private static class NullHolder implements Serializable {
    }

    /**
     * 存入到缓存的key，由缓存的区域+key对象值串接而成
     *
     * @param key key对象
     * @return
     */
    private String getCacheKey(final Object key) {
        return this.name + key.toString();
    }

    /**
     * 缓存名称
     *
     * @param name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * 超时时间(单位:秒)
     *
     * @param expiredDuration
     */
    public void setExpiredDuration(final int expiredDuration) {
        this.expiredDuration = expiredDuration;
    }

    /**
     * 是否允许空值
     *
     * @param allowNullValues
     */
    public void setAllowNullValues(final boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    /**
     * set 最大缓存数量
     *
     * @param maximumSize
     */
    public void setMaximumSize(final Integer maximumSize) {
        this.maximumSize = maximumSize;
    }
}
