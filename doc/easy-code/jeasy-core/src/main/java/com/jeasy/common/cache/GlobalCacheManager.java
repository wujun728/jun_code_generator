package com.jeasy.common.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.transaction.AbstractTransactionSupportingCacheManager;

import java.util.Collection;

/**
 * 缓存管理类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class GlobalCacheManager extends AbstractTransactionSupportingCacheManager {

    private Collection<? extends Cache> caches;

    public GlobalCacheManager() {
    }

    /**
     * Specify the collection of Cache instances to use for this CacheManager.
     */
    public void setCaches(final Collection<? extends Cache> caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.caches;
    }

}
