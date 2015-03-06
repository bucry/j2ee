package com.framework.core.http;

import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author neo
 */
public abstract class HTTPRequest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final String url;
    private final HTTPHeaders headers = new HTTPHeaders();

    protected HTTPRequest(String url) {
        this.url = url;
    }

    public void addHeader(String name, String value) {
        headers.add(name, value);
    }

    public void setAccept(String contentType) {
        headers.add(HTTPConstants.HEADER_ACCEPT, contentType);
    }

    HttpRequestBase createHTTPRequest() throws IOException {
        HttpRequestBase request = createRequest();
        headers.addHeadersToRequest(request);
        return request;
    }

    void logRequest() {
        logger.debug("====== http request begin ======");
        headers.log();
        logRequestParams();
        logger.debug("====== http request end ======");
    }

    abstract HttpRequestBase createRequest() throws IOException;

    protected abstract void logRequestParams();
}
