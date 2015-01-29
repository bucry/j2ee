package com.framework.core.platform.cache;

public class CacheSettings {
    
    private CacheProvider cacheProvider = CacheProvider.EHCACHE;
    
    private String remoteCacheServers;

    public CacheProvider getCacheProvider() {
        return cacheProvider;
    }

    public void setCacheProvider(CacheProvider cacheProvider) {
        this.cacheProvider = cacheProvider;
    }

    public String getRemoteCacheServers() {
        return remoteCacheServers;
    }

    public void setRemoteCacheServers(String remoteCacheServers) {
        this.remoteCacheServers = remoteCacheServers;
    }
}
