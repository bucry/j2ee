package com.framework.core.platform.exception;

/**
 * @author neo
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
