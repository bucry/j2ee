package com.framework.core.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ByteArrayEntity;

import com.framework.core.utils.DigestUtils;

/**
 * @author neo
 */
public class ByteArrayPost extends HTTPRequest {
    byte[] bytes;
    boolean chunked;

    public ByteArrayPost(String url) {
        super(url);
    }

    @Override
    HttpRequestBase createRequest() throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        ByteArrayEntity entity = new ByteArrayEntity(bytes);
        entity.setContentType("binary/octet-stream");
        entity.setChunked(chunked);
        post.setEntity(entity);
        return post;
    }

    @Override
    protected void logRequestParams() {
        logger.debug("[param] bytes=" + DigestUtils.base64(bytes));
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public void setChunked(boolean chunked) {
        this.chunked = chunked;
    }
}
