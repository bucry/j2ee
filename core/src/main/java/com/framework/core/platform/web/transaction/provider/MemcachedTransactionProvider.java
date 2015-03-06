package com.framework.core.platform.web.transaction.provider;

import java.net.SocketAddress;
import java.util.Collection;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import net.spy.memcached.CASValue;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.spring.MemcachedClientFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.framework.core.platform.monitor.ServiceStatus;
import com.framework.core.platform.web.site.TransactionSettings;
import com.framework.core.utils.TimeLength;

public class MemcachedTransactionProvider implements TransactionProvider {
    
    private final Logger logger = LoggerFactory.getLogger(MemcachedTransactionProvider.class);

    private static final TimeLength MEMCACHED_TIME_OUT = TimeLength.seconds(3);

    //通过MemcachedClient可以操作Memcached
    private MemcachedClient memcachedClient;
    
    private TransactionSettings transactionSettings;
    
    @PostConstruct
    public void initialize() throws Exception {
        MemcachedClientFactoryBean factory = new MemcachedClientFactoryBean();
        factory.setServers(transactionSettings.getRemoteTransactionServers());
        factory.setOpTimeout(MEMCACHED_TIME_OUT.toMilliseconds());
        factory.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
        memcachedClient = (MemcachedClient) factory.getObject();
    }

    @PreDestroy
    public void shutdown() {
        logger.info("shutdown memcached transaction client");
        memcachedClient.shutdown();
    }

    @Override
    public String getAndRefreshTransaction(String transactionId) {
        String transactionKey = getCacheKey(transactionId);
        CASValue<Object> cacheValue = memcachedClient.getAndTouch(transactionKey, expirationTime());
        if (cacheValue == null) {
            logger.warn("can not find transaction or transaction expired, transactionKey=" + transactionKey);
            return null;
        }
        return (String) cacheValue.getValue();
    }

    @Override
    public void saveTransaction(String transactionId, String sessionData) {
        memcachedClient.set(getCacheKey(transactionId), expirationTime(), sessionData);        
    }

    @Override
    public void clearTransaction(String transactionId) {
        memcachedClient.delete(getCacheKey(transactionId));
    }

    @Override
    public String getServiceName() {
        return "MemcachedTransaction";
    }

    @Override
    public ServiceStatus getServiceStatus() throws Exception {
        Collection<SocketAddress> availableServers = memcachedClient.getAvailableServers();
        return availableServers.isEmpty() ? ServiceStatus.DOWN : ServiceStatus.UP;
    }
    
    //TODO: use different namespace for secure session?
    private String getCacheKey(String transactionId) {
        return "transaction:" + transactionId;
    }
    
    private int expirationTime() {
        return (int) transactionSettings.getTransactionTimeOut().toSeconds();
    }

    @Inject
    public void setTransactionSettings(TransactionSettings transactionSettings) {
        this.transactionSettings = transactionSettings;
    }
}