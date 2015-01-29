package com.framework.core.platform.cache;


import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Status;

import com.framework.core.platform.monitor.ServiceMonitor;
import com.framework.core.platform.monitor.ServiceStatus;
//import org.springframework.cache.ehcache.EhCacheCacheManager;

public class EhcacheCacheManager /** extends EhCacheCacheManager **/ implements ServiceMonitor {

    public EhcacheCacheManager() {
        //setCacheManager(new net.sf.ehcache.CacheManager(new net.sf.ehcache.config.Configuration()));
    }

    public void addCache(Cache cache) {
        //getCacheManager().addCache(cache);
    }

    @Override
    public ServiceStatus getServiceStatus() throws Exception {
        //CacheManager cacheManager = getCacheManager();
       // boolean alive = Status.STATUS_ALIVE.equals(cacheManager.getStatus());
       // if (alive) return ServiceStatus.UP;
        return ServiceStatus.DOWN;
    }

    @Override
    public String getServiceName() {
        return "Ehcache";
    }
}