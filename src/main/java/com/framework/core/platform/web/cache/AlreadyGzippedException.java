package com.framework.core.platform.web.cache;

import net.sf.ehcache.CacheException;

public class AlreadyGzippedException extends CacheException {

    public AlreadyGzippedException() {
        super();
    }

    public AlreadyGzippedException(String message) {
        super(message);
    }
    
}