package com.framework.core.exception;

/**
 * @author bfc
 */
public class UserAuthorizationException extends RuntimeException {
    
	private static final long serialVersionUID = 5336363652287070633L;

	public UserAuthorizationException(String message) {
        super(message);
    }

    public UserAuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }
}
