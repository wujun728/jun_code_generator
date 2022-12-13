package com.jeasy.common.cache.ehcache;

import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.Status;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;

/**
 * ehcache缓存实现
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class EhcacheCache implements org.springframework.cache.Cache {

    private static final int NUM_50 = 50;
    private final Ehcache cache;

    /**
     * 默认使用fastjson做为序列化与反序列化工具
     */
    private IEhcacheSerializer ehcacheSerializer = new EhcacheFastjsonSerializer();

    /**
     * Create an {@link EhCacheCache} instance.
     *
     * @param ehcache backing Ehcache instance
     */
    public EhcacheCache(final Ehcache ehcache) {
        Assert.notNull(ehcache, "Ehcache must not be null");
        Status status = ehcache.getStatus();
        Assert.isTrue(Status.STATUS_ALIVE.equals(status), "An 'alive' Ehcache is required - current cache is " + status.toString());
        this.cache = ehcache;
    }

    @Override
    public final String getName() {
        return this.cache.getName();
    }

    @Override
    public final Ehcache getNativeCache() {
        return this.cache;
    }

    @Override
    public ValueWrapper get(final Object key) {
        String cacheKey = getCacheKey(key);
        try {
            StopWatch sw = new StopWatch();
            sw.start();
            Element element = this.cache.get(cacheKey);
            sw.stop();
            if (sw.getTime() > NUM_50) {
                log.info(StrKit.format("读取ehcache cache用时{}, key={}, cacheName={}", sw.getTime(), cacheKey, this.getName()));
            }
            if (element != null) {
                String object = (String) element.getObjectValue();
                return new SimpleValueWrapper(this.ehcacheSerializer.deserialize(object));
            } else {
                return null;
            }
        } catch (Exception e) {
            log.error(StrKit.format("读取ehcache cache缓存发生异常, key={}, cacheName={}", cacheKey, this.getName()), e);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(final Object key, final Class<T> type) {
        String cacheKey = getCacheKey(key);
        Element element = this.cache.get(cacheKey);
        Object value = (element != null ? element.getObjectValue() : null);
        if (value != null && type != null && !type.isInstance(value)) {
            throw new IllegalStateException("Cached value is not of required type [" + type.getName() + "]: " + value);
        }
        String v = (String) value;
        if (v == null) {
            return null;
        } else {
            try {
                return (T) this.ehcacheSerializer.deserialize(v);
            } catch (Exception e) {
                log.error(StrKit.S_EMPTY, e);
                return null;
            }
        }
    }

    @Override
    public <T> T get(final Object key, final Callable<T> valueLoader) {
        return null;
    }

    @Override
    public void put(final Object key, final Object value) {
        try {
            String v = this.ehcacheSerializer.serialize(value);
            String cacheKey = getCacheKey(key);
            this.cache.put(new Element(cacheKey, v));
        } catch (Exception ex) {
            log.error(StrKit.S_EMPTY, ex);
        }

    }

    @Override
    public ValueWrapper putIfAbsent(final Object key, final Object value) {
        return null;
    }

    @Override
    public void evict(final Object key) {
        this.cache.remove(key);
    }

    @Override
    public void clear() {
        this.cache.removeAll();
    }

    /**
     * 存入到缓存的key，由缓存的区域+key对象值串接而成
     *
     * @param key key对象
     * @return
     */
    private String getCacheKey(final Object key) {
        return this.cache.getName() + key.toString();
    }

    /**
     * get ehcacheSerializer
     *
     * @return
     */
    public IEhcacheSerializer getEhcacheSerializer() {
        return ehcacheSerializer;
    }

    /**
     * set ehcacheSerializer
     *
     * @param ehcacheSerializer
     */
    public void setEhcacheSerializer(final IEhcacheSerializer ehcacheSerializer) {
        this.ehcacheSerializer = ehcacheSerializer;
    }
}
