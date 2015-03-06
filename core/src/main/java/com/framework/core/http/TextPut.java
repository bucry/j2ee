package com.framework.core.http;

import java.io.UnsupportedEncodingException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

public class TextPut extends HTTPRequest {
    
    String body;
    String contentType = HTTPConstants.CONTENT_TYPE_PLAIN;
    boolean chunked;

    public TextPut(String url) {
        super(url);
    }

    @Override
    HttpRequestBase createRequest() throws UnsupportedEncodingException {
        HttpPut put = new HttpPut(url);
        AbstractHttpEntity entity = new StringEntity(body, HTTP.UTF_8);
        entity.setContentType(contentType + HTTP.CHARSET_PARAM + HTTP.UTF_8);
        entity.setChunked(chunked);
        put.setEntity(entity);
        return put;
    }

    @Override
    protected void logRequestParams() {
        logger.debug("contentType=" + contentType);
        logger.debug("[param] body=" + body);
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setChunked(boolean chunked) {
        this.chunked = chunked;
    }
}
