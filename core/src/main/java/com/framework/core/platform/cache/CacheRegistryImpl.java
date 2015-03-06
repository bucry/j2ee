package com.framework.core.platform.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;

import org.springframework.cache.CacheManager;

import com.framework.core.utils.AssertUtils;
import com.framework.core.utils.TimeLength;

public class CacheRegistryImpl implements CacheRegistry {

    private final CacheManager cacheManager;

    public CacheRegistryImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void addCache(String cacheName, TimeLength expirationTime) {
        if (cacheManager instanceof MemcachedCacheManager) {
            ((MemcachedCacheManager) cacheManager).add(cacheName, expirationTime);
        } else {
            // for dev env, use simple hardcoded config
            addCache(cacheName, expirationTime, 0);
        }
    }

    @Override
    public void addCache(String cacheName, TimeLength expirationTime, int maxEntriesInHeap) {
        AssertUtils.assertTrue(cacheManager instanceof EhcacheCacheManager, "must use ehcache manager, for memcached, please use addCache method");
        // for dev env, use simple hardcoded config
        CacheConfiguration cacheConfiguration = new CacheConfiguration(cacheName, maxEntriesInHeap);
        cacheConfiguration.setTimeToLiveSeconds(expirationTime.toSeconds());
        ((EhcacheCacheManager) cacheManager).addCache(new Cache(cacheConfiguration));
    }

}
