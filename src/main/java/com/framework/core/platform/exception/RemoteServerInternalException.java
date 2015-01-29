package com.framework.core.platform.exception;

/**
 * Created with IntelliJ IDEA.
 * User: Wayne
 * Date: 12-8-29
 * Time: 下午2:38
 * To change this template use File | Settings | File Templates.
 */
public class RemoteServerInternalException extends  RuntimeException {

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
