package com.framework.core.exception;

/**
 * @author bfc
 */
public class TypeConversionException extends RuntimeException {
	
	private static final long serialVersionUID = 4792158407228001331L;

	public TypeConversionException(String message) {
        super(message);
    }

    public TypeConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
