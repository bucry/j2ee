package com.framework.core.http;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;

/**
 * @author neo
 */
public class TextPost extends HTTPRequest {
    String body;
    String contentType = HTTPConstants.CONTENT_TYPE_PLAIN;
    boolean chunked;

    public TextPost(String url) {
        super(url);
    }

    @Override
    HttpRequestBase createRequest() throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        AbstractHttpEntity entity = new StringEntity(body, HTTP.UTF_8);
        entity.setContentType(contentType + HTTP.CHARSET_PARAM + HTTP.UTF_8);
        entity.setChunked(chunked);
        post.setEntity(entity);
        return post;
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
