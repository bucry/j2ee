package com.framework.core.http;

/**
 * @author neo
 */
public class HTTPStatusCode {
    private final int statusCode;

    public HTTPStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public boolean isRedirect() {
        return statusCode == HTTPConstants.SC_MOVED_TEMPORARILY
                || statusCode == HTTPConstants.SC_MOVED_PERMANENTLY
                || statusCode == HTTPConstants.SC_TEMPORARY_REDIRECT
                || statusCode == HTTPConstants.SC_SEE_OTHER;
    }

    public boolean isSuccess() {
        return statusCode >= HTTPConstants.SC_OK
                && statusCode <= HTTPConstants.SC_MULTI_STATUS;
    }

    public boolean isServerError() {
        return statusCode >= HTTPConstants.SC_INTERNAL_SERVER_ERROR
                && statusCode <= HTTPConstants.SC_INSUFFICIENT_STORAGE;
    }

    @Override
    public String toString() {
        return String.valueOf(statusCode);
    }
}
