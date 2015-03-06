package com.framework.core.http;

/**
 * @author neo
 */
public class HTTPBinaryResponse {
    final HTTPStatusCode statusCode;
    final HTTPHeaders headers;
    final byte[] responseContent;

    public HTTPBinaryResponse(HTTPStatusCode statusCode, HTTPHeaders headers, byte[] responseContent) {
        this.headers = headers;
        this.statusCode = statusCode;
        this.responseContent = responseContent;
    }

    public byte[] getResponseContent() {
        return responseContent;
    }

    public HTTPStatusCode getStatusCode() {
        return statusCode;
    }

    public HTTPHeaders getHeaders() {
        return headers;
    }
}

