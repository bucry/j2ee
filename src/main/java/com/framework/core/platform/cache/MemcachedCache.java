package com.framework.core.platform.cache;

import java.util.HashSet;
import java.util.Set;

import net.spy.memcached.MemcachedClient;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import com.framework.core.utils.TimeLength;

public class MemcachedCache implements Cache {

    private final String cacheName;
    private final MemcachedClient memcachedClient;
    private final TimeLength expirationTime;

    public MemcachedCache(String cacheName, TimeLength expirationTime, MemcachedClient memcachedClient) {
        this.cacheName = cacheName;
        this.expirationTime = expirationTime;
        this.memcachedClient = memcachedClient;
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        return memcachedClient;
    }

    @Override
    public ValueWrapper get(Object key) {
        Object value = memcachedClient.get(constructKey(key));
        if (value == null) return null;
        return new SimpleValueWrapper(value);
    }

    private String constructKey(Object key) {
        return cacheName + ":" + key.toString();
    }

    @Override
    public void put(Object key, Object value) {
        if (null == value)
            return;
        memcachedClient.set(constructKey(key), (int) expirationTime.toSeconds(), value);
        // add the key to the key set which located in the memcache
        addKeyToCachedKeySet(key);
    }

    private void addKeyToCachedKeySet(Object key) {
        Set<String> keys = fetchKeySet();
        keys.add(constructKey(key));
        memcachedClient.set(generateKeyOfKeySet(), 0, keys);
    }

    @SuppressWarnings("unchecked")
    private Set<String> fetchKeySet() {
        Object obj = memcachedClient.get(generateKeyOfKeySet());
        return obj == null ? new HashSet<String>() : (Set<String>) obj;
    }

    @Override
    public void evict(Object key) {
        memcachedClient.delete(constructKey(key));
    }

    @Override
    public void clear() {
        //clear the real cache specified in the key set
        Set<String> keys = fetchKeySet();
        for (String key : keys)
            memcachedClient.delete(key);
        //clear the key set cache
        memcachedClient.delete(generateKeyOfKeySet());
    }
    
    //generate key of keySet
    private String generateKeyOfKeySet() {
        return cacheName + "__KEY__";
    }

	@Override
	public <T> T get(Object key, Class<T> type) {
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return null;
	}

}
