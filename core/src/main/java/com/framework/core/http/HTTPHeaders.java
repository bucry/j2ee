package com.framework.core.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author neo
 */
public class HTTPHeaders {
    static HTTPHeaders createResponseHeaders(HttpResponse response) {
        Header[] rawHeaders = response.getAllHeaders();
        List<HTTPHeader> httpHeaders = new ArrayList<HTTPHeader>(rawHeaders.length);
        for (Header header : rawHeaders) {
            httpHeaders.add(new HTTPHeader(header.getName(), header.getValue()));
        }

        HTTPHeaders headers = new HTTPHeaders();
        headers.headers = Collections.unmodifiableList(httpHeaders);
        return headers;
    }

    private final Logger logger = LoggerFactory.getLogger(HTTPHeaders.class);

    // header's name can be duplicated according to HTTP Spec, so use List not Map
    List<HTTPHeader> headers;

    public void add(String name, String value) {
        if (headers == null) headers = new ArrayList<HTTPHeader>();
        headers.add(new HTTPHeader(name, value));
    }

    public List<HTTPHeader> getValues() {
        return headers;
    }

    public String getFirstHeaderValue(String name) {
        if (headers != null) {
            for (HTTPHeader header : headers) {
                if (header.getName().equals(name)) return header.getValue();
            }
        }
        return null;
    }

    void addHeadersToRequest(HttpRequestBase request) {
        if (headers != null) {
            for (HTTPHeader header : headers) {
                request.addHeader(header.getName(), header.getValue());
            }
        }
    }

    void log() {
        if (headers != null) {
            for (HTTPHeader header : headers) {
                logger.debug("[header] " + header.getName() + "=" + header.getValue());
            }
        }
    }
}
