package com.jeasy.common.cache.j2cache;

import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.J2Cache;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Callable;

/**
 * 基于spring的 J2Cache实现（两级缓存）
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class TwoLevelCache implements Cache, InitializingBean {

    private static final Object NULL_HOLDER = new NullHolder();
    public static final int NUM_50 = 50;

    /**
     * 缓存名称
     */
    private String name;

    /**
     * J2Cache客户端
     */
    private CacheChannel j2Cache;

    private boolean allowNullValues = true;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.j2Cache == null) {
            this.j2Cache = J2Cache.getChannel();
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public CacheChannel getNativeCache() {
        return this.j2Cache;
    }

    @Override
    public ValueWrapper get(final Object key) {
        String cacheKey = getCacheKey(key);
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Object value = this.j2Cache.get(name, cacheKey);
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
            Object value = fromStoreValue(this.j2Cache.get(name, cacheKey));
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
        try {
            this.j2Cache.set(name, getCacheKey(key), toStoreValue(value));
        } catch (IOException e) {
            log.error("TwoLevelCache put occur exception : ", e);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(final Object key, final Object value) {
        return null;
    }

    @Override
    public void evict(final Object key) {
        try {
            this.j2Cache.evict(this.getName(), getCacheKey(key));
        } catch (IOException e) {
            log.error("TwoLevelCache evict occur exception : ", e);
        }
    }

    @Override
    public void clear() {
        try {
            this.j2Cache.clear(this.getName());
        } catch (IOException e) {
            log.error("TwoLevelCache clear occur exception : ", e);
        }
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
    protected Serializable toStoreValue(final Object userValue) {
        if (this.allowNullValues && userValue == null) {
            return (Serializable) NULL_HOLDER;
        }
        return (Serializable) userValue;
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
     * 是否允许空值
     *
     * @param allowNullValues
     */
    public void setAllowNullValues(final boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }
}
