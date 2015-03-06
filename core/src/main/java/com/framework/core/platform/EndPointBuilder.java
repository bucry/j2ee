package com.framework.core.platform;

import com.framework.core.exception.BusinessProcessException;
import com.framework.core.utils.StringUtils;

public final class EndPointBuilder<T> {
    
    private EndPoint endPoint;

    private String action;
    
    private String appKey;
    
    private String bodyContent;
    
    private Class<T> responseClass;
    
    private Class<?>[] elementClasses;
    
    private  EndPointBuilder() { 
    }
    
    public static <T> EndPointBuilder<T> create(Class<T> targetClass) {
        if (null == targetClass) throw new BusinessProcessException("targetClass不能为空.");
        EndPointBuilder<T> endPointBuilder = new EndPointBuilder<T>();
        endPointBuilder.target(targetClass);
        return endPointBuilder;
    }
    
    public EndPointBuilder<T> elementTypes(Class<?>... elementClasses) {
        this.elementClasses = elementClasses;
        return this;
    }
    
    public EndPointBuilder<T> appKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public EndPointBuilder<T> endpoint(EndPoint endPoint) {
        this.endPoint = endPoint;
        return this;
    }

    public EndPointBuilder<T> action(String action) {
        this.action = action;
        return this;
    }
    
    public EndPointBuilder<T> arguments(Object... arguments) {
        this.action(String.format(action, arguments));
        return this;
    } 

    public EndPointBuilder<T> body(String bodyContent) {
        this.bodyContent = bodyContent;
        return this;
    }

    private EndPointBuilder<T> target(Class<T> responseClass) {
        this.responseClass = responseClass;
        return this;
    } 

    public String getService() {
        if (!StringUtils.hasText(appKey)) throw new BusinessProcessException("appKey不能为空.");
        if (null == endPoint) throw new BusinessProcessException("endPoint不能为空.");
        if (!StringUtils.hasText(action)) throw new BusinessProcessException("action不能为空.");
        return new StringBuilder(endPoint.getEndpoint()).append(action).toString();
    }

    public String getAppKey() {
        return appKey;
    }

    public String getBodyContent() {
        return bodyContent;
    }

    public Class<T> getResponseClass() {
        return responseClass;
    }

    public Class<?>[] getElementClasses() {
        return elementClasses;
    }
    
    
}
