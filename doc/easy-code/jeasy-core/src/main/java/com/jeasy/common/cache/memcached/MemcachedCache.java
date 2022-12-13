package com.jeasy.common.cache.memcached;

import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 基于Spring Cache抽象体系的Memcached缓存实现
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class MemcachedCache implements Cache, InitializingBean {

    /**
     * 默认最长缓存时间为1小时
     */
    private static final int MAX_EXPIRED_DURATION = 60 * 60;

    /**
     * Null值的最长缓存时间
     */
    private static final int NULL_VALUE_EXPIRATION = 60 * 60 * 24 * 7;

    /**
     * 增量过期时间允许设置的最大值
     */
    private static final int DELTA_EXPIRATION_THRESHOLD = 60 * 60 * 24 * 30;

    private static final Object NULL_HOLDER = new NullHolder();
    public static final int NUM_50 = 50;

    private String name;

    private MemcachedClient memcachedClient;

    /**
     * 缓存数据超时时间
     */
    private int expiredDuration = MAX_EXPIRED_DURATION;

    private boolean allowNullValues = true;

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(memcachedClient, "memcachedClient must not be null!");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public MemcachedClient getNativeCache() {
        return this.memcachedClient;
    }

    @Override
    public ValueWrapper get(final Object key) {
        String cacheKey = getCacheKey(key);
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Object value = memcachedClient.get(cacheKey);
            sw.stop();
            if (sw.getTime() > NUM_50) {
                log.info(StrKit.format("读取memcached用时{}, key={}", sw.getTime(), cacheKey));
            }
            return (value != null ? new SimpleValueWrapper(fromStoreValue(value)) : null);
        } catch (Exception e) {
            log.error(StrKit.format("读取memcached缓存发生异常, key={}, server={}", cacheKey, memcachedClient.getNodeLocator().getPrimary(cacheKey).getSocketAddress()), e.getCause());
            return null;
        }
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
     * 安全的Set方法,在3秒内返回结果, 否则取消操作.
     */
    @Override
    public void put(final Object key, Object value) {
        String cacheKey = getCacheKey(key);
        log.debug(StrKit.format("放入缓存的Key:{}, Value:{}, StoreValue:{}", cacheKey, value, toStoreValue(value)));
        int expiration = expiredDuration;
        if (value == null) {
            if (allowNullValues) {
                // 若允许缓存空值，则替换null为占坑对象；不允许直接缓存null，因为无法序列化
                value = NULL_HOLDER;
            }
            if (expiredDuration > NULL_VALUE_EXPIRATION) {
                // 缩短空值的过期时间，最长缓存7天
                expiration = NULL_VALUE_EXPIRATION;
            }
        } else if (expiredDuration > DELTA_EXPIRATION_THRESHOLD) {
            // 修改为UNIX时间戳类型的过期时间，使能够设置超过30天的过期时间
            expiration += (int) (System.currentTimeMillis() / 1000);
            // 注意：时间戳计算这里有2038问题，
            // 2038-1-19
            // 11:14:07
            // (GMT
            // +8)
            // 后，转换成的
            // int
            // 会溢出，导致出现负值
        }

        Future<Boolean> future = memcachedClient.set(cacheKey, expiration, value);
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
            log.error(StrKit.format("memcached写入缓存发生异常, key={}, server={}", cacheKey, memcachedClient.getNodeLocator().getPrimary(cacheKey).getSocketAddress()), e);
        }
    }

    @Override
    public ValueWrapper putIfAbsent(final Object key, final Object value) {
        return null;
    }

    /**
     * 安全的evict方法,在3秒内返回结果, 否则取消操作.
     */
    @Override
    public void evict(final Object key) {
        String cacheKey = getCacheKey(key);
        log.debug(StrKit.format("删除缓存的Key:{}", cacheKey));
        Future<Boolean> future = memcachedClient.delete(cacheKey);
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (Exception e) {
            future.cancel(false);
            log.error(StrKit.format("memcached清除缓存出现异常, key={}, server={}", cacheKey, memcachedClient.getNodeLocator().getPrimary(cacheKey).getSocketAddress()), e);
        }
    }

    @Override
    public void clear() {
        try {
            memcachedClient.flush();
        } catch (Exception e) {
            log.error("memcached执行flush出现异常", e);
        }
    }

    protected Object fromStoreValue(final Object storeValue) {
        if (this.allowNullValues && storeValue instanceof NullHolder) {
            return null;
        }
        return storeValue;
    }

    private static class NullHolder implements Serializable {

        private static final long serialVersionUID = -99681708140860560L;
    }

    protected Object toStoreValue(final Object userValue) {
        if (this.allowNullValues && userValue == null) {
            return NULL_HOLDER;
        }
        return userValue;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setMemcachedClient(final MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

    public void setExpiredDuration(final int expiredDuration) {
        this.expiredDuration = expiredDuration;
    }

    public void setAllowNullValues(final boolean allowNullValues) {
        this.allowNullValues = allowNullValues;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Object key, final Class<T> type) {
        String cacheKey = getCacheKey(key);
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Object value = memcachedClient.get(cacheKey);
            sw.stop();
            if (sw.getTime() > NUM_50) {
                log.info(StrKit.format("读取memcached用时{}, key={}", sw.getTime(), cacheKey));
            }
            return (T) value;
        } catch (Exception e) {
            log.error(StrKit.format("读取memcached缓存发生异常, key={}, server={}", cacheKey, memcachedClient.getNodeLocator().getPrimary(cacheKey).getSocketAddress()), e.getCause());
            return null;
        }
    }

    @Override
    public <T> T get(final Object key, final Callable<T> valueLoader) {
        return null;
    }
}
