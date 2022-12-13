package com.jeasy.common.cache.ehcache;

import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * memcached 缓存管理类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class EhcacheCacheManager extends AbstractTransactionSupportingCacheManager {

    /**
     * ehcache命名空间的xml文件名
     */
    private String ehcacheFileName;

    /** */
    private net.sf.ehcache.CacheManager cacheManager;

    public EhcacheCacheManager() {
        invokeOnClose();
    }

    @Override
    protected Collection<Cache> loadCaches() {
        net.sf.ehcache.CacheManager cacheManager = getCacheManager();
        Assert.notNull(cacheManager, "A backing EhCache CacheManager is required");
        Status status = cacheManager.getStatus();
        Assert.isTrue(Status.STATUS_ALIVE.equals(status), "An 'alive' EhCache CacheManager is required - current cache is " + status.toString());

        String[] names = cacheManager.getCacheNames();
        Collection<Cache> caches = new LinkedHashSet<Cache>(names.length);
        for (String name : names) {
            caches.add(new EhcacheCache(cacheManager.getEhcache(name)));
        }
        return caches;
    }

    @Override
    protected Cache getMissingCache(String name) {
        // Check the EhCache cache again (in case the cache was added at runtime)
        Ehcache ehcache = getCacheManager().getEhcache(name);
        if (ehcache != null) {
            return new EhCacheCache(ehcache);
        }
        return null;
    }

    /**
     * 注册到应用退出时，会把Ehcache持 久化到磁盘
     */
    private void invokeOnClose() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {

                List<CacheManager> knownCacheManagers = CacheManager.ALL_CACHE_MANAGERS;

                while (!knownCacheManagers.isEmpty()) {
                    // 关闭的时候 刷到磁盘
                    ((CacheManager) CacheManager.ALL_CACHE_MANAGERS.get(0)).shutdown();
                }

            }
        });
    }

    /**
     * 获取cacheManager
     */
    private net.sf.ehcache.CacheManager getCacheManager() {
        if (this.cacheManager == null) {
            try {
                Resource configLocation = new ClassPathResource(this.ehcacheFileName);
                InputStream is = configLocation.getInputStream();
                Configuration configuration = (is != null ? ConfigurationFactory.parseConfiguration(is) : ConfigurationFactory.parseConfiguration());
                this.cacheManager = CacheManager.create(configuration);
            } catch (IOException e) {
                log.error(StrKit.S_EMPTY, e);
            }
        }
        return this.cacheManager;
    }

    /**
     * get ehcache命名空间的xml文件名
     */
    public String getEhcacheFileName() {
        return ehcacheFileName;
    }

    /**
     * set ehcache命名空间的xml文件名
     */
    public void setEhcacheFileName(final String ehcacheFileName) {
        this.ehcacheFileName = ehcacheFileName;
    }

}
