package com.framework.core.http;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class Delete extends HTTPRequest {
    
    List<NameValuePair> parameters;

    public Delete(String url) {
        super(url);
    }

    public void addParameter(String key, String value) {
        if (parameters == null) parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair(key, value));
    }

    @Override
    HttpRequestBase createRequest() {
        String completeURL = createURL();
        return new HttpDelete(completeURL);
    }

    @Override
    protected void logRequestParams() {
        if (parameters != null)
            for (NameValuePair parameter : parameters) {
                logger.debug("[param] " + parameter.getName() + "=" + parameter.getValue());
            }
    }

    String createURL() {
        if (parameters != null) {
            String queryChar = url.contains("?") ? "&" : "?";
            return url + queryChar + URLEncodedUtils.format(parameters, HTTP.DEFAULT_CONTENT_CHARSET);
        }
        return url;
    }

}
