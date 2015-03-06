package com.framework.core.web.request;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.framework.core.utils.ReadOnly;

public class RequestContext {
    
    
    private final ReadOnly<HttpServletRequest> request = new ReadOnly<HttpServletRequest>();
    
    private final ReadOnly<String> clientId = new ReadOnly<String>();
    
    private final ReadOnly<String> requestId = new ReadOnly<String>();
    
    private final ReadOnly<Date> requestDate = new ReadOnly<Date>();
    
    private final ReadOnly<String> action = new ReadOnly<String>();  // the action method, controllerClass-method


    public boolean isSecure() {
        return request.value().isSecure();
    }

    // contextPath of application server
    public String getContextPath() {
        return request.value().getContextPath();
    }

    public RemoteAddress getRemoteAddress() {
        return RemoteAddress.create(request.value());
    }

    public String getClientId() {
        return clientId.value();
    }

    public void setClientId(String clientId) {
        this.clientId.set(clientId);
    }

    public String getRequestId() {
        return requestId.value();
    }

    public void setRequestId(String requestId) {
        this.requestId.set(requestId);
    }

    public Date getRequestDate() {
        return requestDate.value();
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate.set(requestDate);
    }

    public String getAction() {
        return action.value();
    }

    public void setAction(String action) {
        this.action.set(action);
    }

    public void setHTTPRequest(HttpServletRequest request) {
        this.request.set(request);
    }
    

}
