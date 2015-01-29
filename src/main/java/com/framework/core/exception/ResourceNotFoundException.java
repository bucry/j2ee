package com.framework.core.exception;

/**
 * @author bfc
 */
public class ResourceNotFoundException extends RuntimeException {
   
	private static final long serialVersionUID = -3242260315118825244L;

	public ResourceNotFoundException(String message) {
        super(message);
    }
}
