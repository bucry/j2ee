package com.framework.core.exception;

public class RemoteServerInternalException extends  RuntimeException {

	private static final long serialVersionUID = 2203351261404767391L;
	
	private String field;

    public RemoteServerInternalException(String message) {
        this(null, message, null);
    }

    public RemoteServerInternalException(String field, String message) {
        this(field, message, null);
    }

    public RemoteServerInternalException(String field, String message, Throwable cause) {
        super(message, cause);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
