package com.framework.core.platform.cache;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PreDestroy;

import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.spring.MemcachedClientFactoryBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import com.framework.core.platform.monitor.ServiceMonitor;
import com.framework.core.platform.monitor.ServiceStatus;
import com.framework.core.utils.AssertUtils;
import com.framework.core.utils.TimeLength;

public class MemcachedCacheManager extends AbstractCacheManager implements ServiceMonitor {

    private final Logger logger = LoggerFactory.getLogger(MemcachedCacheManager.class);
    private static final TimeLength MEMCACHED_TIME_OUT = TimeLength.seconds(3);

    private final List<MemcachedCache> caches = new ArrayList<MemcachedCache>();
    private MemcachedClient memcachedClient;

    @PreDestroy
    public void shutdown() {
        logger.info("shutdown memcached cache client");
        memcachedClient.shutdown();
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return caches;
    }

    public void add(String cacheName, TimeLength expirationTime) {
        AssertUtils.assertNotNull(memcachedClient, "memcached client must be created before adding cache");
        caches.add(new MemcachedCache(cacheName, expirationTime, memcachedClient));
    }

    public void setServers(String remoteServers) {
        MemcachedClientFactoryBean factory = new MemcachedClientFactoryBean();
        factory.setServers(remoteServers);
        factory.setOpTimeout(MEMCACHED_TIME_OUT.toMilliseconds());
        factory.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
        try {
            memcachedClient = (MemcachedClient) factory.getObject();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public ServiceStatus getServiceStatus() throws Exception {
        Collection<SocketAddress> availableServers = memcachedClient.getAvailableServers();
        return availableServers.isEmpty() ? ServiceStatus.DOWN : ServiceStatus.UP;
    }

    @Override
    public String getServiceName() {
        return "Memcached";
    }
}