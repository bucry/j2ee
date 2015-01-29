package com.framework.core.platform;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

import com.framework.core.platform.cache.CacheProvider;
import com.framework.core.platform.cache.CacheRegistry;
import com.framework.core.platform.cache.CacheRegistryImpl;
import com.framework.core.platform.cache.CacheSettings;
import com.framework.core.platform.cache.DefaultCacheKeyGenerator;
import com.framework.core.platform.cache.EhcacheCacheManager;
import com.framework.core.platform.cache.MemcachedCacheManager;

public abstract class DefaultCacheConfig implements CachingConfigurer {

    @Bean
    public CacheSettings cacheSettings() {
        return new CacheSettings();
    }

    @Override
    @Bean
    public CacheManager cacheManager() {
        CacheManager cacheManager = createCacheManager();
        addCaches(new CacheRegistryImpl(cacheManager));
        return cacheManager;
    }

    private CacheManager createCacheManager() {
        CacheSettings settings = cacheSettings();
        CacheProvider provider = settings.getCacheProvider();
        if (CacheProvider.MEMCACHED.equals(provider)) {
            MemcachedCacheManager cacheManager = new MemcachedCacheManager();
            cacheManager.setServers(settings.getRemoteCacheServers());
            return cacheManager;
        } else if (CacheProvider.EHCACHE.equals(provider)) {
            //return new EhcacheCacheManager();
        	//TODO
        	return null;
        }
        throw new IllegalStateException("not supported cache provider, provider=" + provider);
    }

    @Override
    @Bean
    public KeyGenerator keyGenerator() {
        return new DefaultCacheKeyGenerator();
    }

    protected abstract void addCaches(CacheRegistry registry);

}
