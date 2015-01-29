package com.framework.core.exception;

/**
 * @author bfc
 */
public class InvalidRequestException extends RuntimeException {
    
	private static final long serialVersionUID = 1212496763551374749L;
	
	private String field;

    public InvalidRequestException(String message) {
        this(null, message, null);
    }

    public InvalidRequestException(String field, String message) {
        this(field, message, null);
    }

    public InvalidRequestException(String field, String message, Throwable cause) {
        super(message, cause);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
