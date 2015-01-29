package com.framework.core.platform.web.cache;

import net.sf.ehcache.CacheException;

public class ResponseHeadersNotModifiableException extends CacheException {

    public ResponseHeadersNotModifiableException() {
        super();
    }

    public ResponseHeadersNotModifiableException(String message) {
        super(message);
    }
}
