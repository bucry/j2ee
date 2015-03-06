package com.framework.core.collection;

/**
 * @author neo
 */
public class TypeConversionException extends RuntimeException {

	private static final long serialVersionUID = -2846816075425079811L;

	public TypeConversionException(String message) {
        super(message);
    }

    public TypeConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
