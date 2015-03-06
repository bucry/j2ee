package com.framework.core.platform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.core.http.HttpManager;
import com.framework.core.json.JSONBinder;

public final class NstechsApiClientService {

    private final Logger logger = LoggerFactory.getLogger(NstechsApiClientService.class);

    public static final String ATTRIBUTE_CLIENT_KEY = "WS-APPKEY";
    
    public <T> T post(EndPointBuilder<T> builder) {
        return null;
    }

    public <T> T put(EndPointBuilder<T> builder) {
    	return null;
    }

    public <T> T get(EndPointBuilder<T> builder) {
    	logger.debug("get method is run");
    	return JSONBinder.binder(builder.getResponseClass()).fromJSON(HttpManager.get(builder.getService()));
    }
    
    public <T> T delete(EndPointBuilder<T> builder) {
    	
    	return null;
    }


    
    public void validateResponse(String responseText) {
        if (responseText.startsWith("{\"status\":\"FAILED\"")) {
           
        }
    }
}