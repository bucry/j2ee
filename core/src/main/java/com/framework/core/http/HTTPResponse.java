package com.framework.core.http;

/**
 * @author neo
 */
public class HTTPResponse {
    final HTTPStatusCode statusCode;
    final HTTPHeaders headers;
    final String responseText;

    public HTTPResponse(HTTPStatusCode statusCode, HTTPHeaders headers, String responseText) {
        this.headers = headers;
        this.statusCode = statusCode;
        this.responseText = responseText;
    }

    public String getResponseText() {
        return responseText;
    }

    public HTTPStatusCode getStatusCode() {
        return statusCode;
    }

    public HTTPHeaders getHeaders() {
        return headers;
    }
}
