package com.framework.core.platform.exception;

public class WsClientInValidateException extends RuntimeException {
    
    public WsClientInValidateException(String message) {
        super(message);
    }

    public WsClientInValidateException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
