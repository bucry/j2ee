package com.framework.core.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.AbstractHttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class FormPut extends HTTPRequest {
    final List<NameValuePair> parameters = new ArrayList<NameValuePair>();
    boolean chunked;

    public FormPut(String url) {
        super(url);
    }

    public void addParameter(String key, String value) {
        parameters.add(new BasicNameValuePair(key, value));
    }

    public void setParameter(String key, String value) {
        for (Iterator<NameValuePair> iterator = parameters.listIterator(); iterator.hasNext(); ) {
            NameValuePair param = iterator.next();
            if (param.getName().equals(key))
                iterator.remove();
        }
        addParameter(key, value);
    }

    @Override
    HttpRequestBase createRequest() throws UnsupportedEncodingException {
        HttpPut put = new HttpPut(url);
        AbstractHttpEntity entity = new UrlEncodedFormEntity(parameters, HTTP.UTF_8);
        entity.setChunked(chunked);
        put.setEntity(entity);
        return put;
    }

    @Override
    protected void logRequestParams() {
        for (NameValuePair parameter : parameters) {
            logger.debug("[param] " + parameter.getName() + "=" + parameter.getValue());
        }
    }

    public void setChunked(boolean chunked) {
        this.chunked = chunked;
    }
}