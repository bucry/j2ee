package com.framework.core.platform.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.core.http.Delete;
import com.framework.core.http.Get;
import com.framework.core.http.HTTPBinaryResponse;
import com.framework.core.http.HTTPClient;
import com.framework.core.http.HTTPConstants;
import com.framework.core.http.HTTPResponse;
import com.framework.core.http.HTTPStatusCode;
import com.framework.core.http.TextPost;
import com.framework.core.http.TextPut;
import com.framework.core.json.JSONBinder;
import com.framework.core.platform.exception.WsClientInValidateException;
import com.framework.core.platform.web.rest.ErrorResponse;
import com.framework.core.utils.StringUtils;

public final class NstechsApiClientService {

    private final Logger logger = LoggerFactory.getLogger(NstechsApiClientService.class);

    private static final String ATTRIBUTE_CLIENT_KEY = "WS-APPKEY";
    
    public <T> T post(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        TextPost post = new TextPost(builder.getService());
        post.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        post.setContentType(HTTPConstants.CONTENT_TYPE_JSON);
        post.addHeader(ATTRIBUTE_CLIENT_KEY, builder.getAppKey());
        if (StringUtils.hasText(builder.getBodyContent())) {
            post.setBody(builder.getBodyContent());
        }
        return convertResponse(builder, httpClient.execute(post));
    }

    public <T> T put(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        TextPut put = new TextPut(builder.getService());
        put.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        put.setContentType(HTTPConstants.CONTENT_TYPE_JSON);
        put.addHeader(ATTRIBUTE_CLIENT_KEY, builder.getAppKey());
        if (StringUtils.hasText(builder.getBodyContent())) {
            put.setBody(builder.getBodyContent());
        }
        return convertResponse(builder, httpClient.execute(put));
    }

    public <T> T get(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        Get get = new Get(builder.getService());
        get.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        get.addHeader(ATTRIBUTE_CLIENT_KEY, builder.getAppKey());
        return convertResponse(builder, httpClient.execute(get));
    }
    
    public <T> T delete(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        Delete delete = new Delete(builder.getService());
        delete.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        delete.addHeader(ATTRIBUTE_CLIENT_KEY, builder.getAppKey());
        return convertResponse(builder, httpClient.execute(delete));
    }

    public <T> HTTPBinaryResponse download(EndPointBuilder<T> builder) {
        HTTPClient httpClient = createHTTPClient();
        TextPost post = new TextPost(builder.getService());
        post.setAccept(HTTPConstants.CONTENT_TYPE_JSON);
        post.setContentType(HTTPConstants.CONTENT_TYPE_JSON);
        if (StringUtils.hasText(builder.getBodyContent())) {
            post.setBody(builder.getBodyContent());
        }
        return httpClient.download(post);
    }

    public <T> T convertResponse(EndPointBuilder<T> builder, HTTPResponse response) {
        HTTPStatusCode statusCode = response.getStatusCode();
        logger.debug("response status code => {}", statusCode.getStatusCode());
        String responseText = response.getResponseText();
        if (!statusCode.isSuccess()) {
            throw new WsClientInValidateException("Failed to call api service, responseText=" + responseText + ", statusCode=" + statusCode.getStatusCode());
        }
        validateResponse(responseText);
        return JSONBinder.binder(builder.getResponseClass(), builder.getElementClasses()).fromJSON(responseText);
    }
    
    public void validateResponse(String responseText) {
        if (responseText.startsWith("{\"status\":\"FAILED\"")) {
            ErrorResponse errorResponse = JSONBinder.binder(ErrorResponse.class).fromJSON(responseText);
            String error = errorResponse.getMessage();
            throw new WsClientInValidateException(error);
        }
    }

    private HTTPClient createHTTPClient() {
        HTTPClient httpClient = new HTTPClient();
        httpClient.setValidateStatusCode(false);
        return httpClient;
    }
    
}