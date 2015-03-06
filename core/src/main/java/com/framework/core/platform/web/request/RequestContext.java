package com.framework.core.platform.web.request;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import com.framework.core.platform.web.DeploymentEnvironment;
import com.framework.core.platform.web.DeploymentSettings;
import com.framework.core.platform.web.filter.RequestUtils;
import com.framework.core.utils.ReadOnly;
import com.framework.core.utils.StringUtils;

public class RequestContext {
    
    private DeploymentSettings deploymentSettings;
    
    private final ReadOnly<HttpServletRequest> request = new ReadOnly<HttpServletRequest>();
    
    private final ReadOnly<String> clientId = new ReadOnly<String>();
    
    private final ReadOnly<String> requestId = new ReadOnly<String>();
    
    private final ReadOnly<Date> requestDate = new ReadOnly<Date>();
    
    private final ReadOnly<String> action = new ReadOnly<String>();  // the action method, controllerClass-method

    public String getScheme() {
        return  DeploymentEnvironment.DEV.equals(deploymentSettings.getEnvironment()) ? request.value().getScheme() : request.value().getHeader("X-Forwarded-Proto"); 
    }
    
    public String getRemoteAddr() {
        return DeploymentEnvironment.DEV.equals(deploymentSettings.getEnvironment()) ? request.value().getRemoteAddr() : request.value().getHeader("X-Forwarded-For");
    }
    
    public String getServerName() {
        return DeploymentEnvironment.DEV.equals(deploymentSettings.getEnvironment()) ? request.value().getServerName() : StringUtils.hasText(deploymentSettings.getHost()) ? deploymentSettings.getHost() : request.value().getHeader("Host");
    }

    public String getRequestURLWithQueryString() {
        return RequestUtils.getRequestURLWithQueryString(request.value());
    }

    public String getRelativeRequestURLWithQueryString() {
        return RequestUtils.getRelativeRequestURLWithQueryString(request.value());
    }

    public String getClientRelativeRequestURLWithQueryString() {
        return RequestUtils.getClientRelativeRequestURLWithQueryString(request.value());
    }
    
    public String getRelativeRequestURL() {
        return RequestUtils.getRelativeRequestURL(request.value());
    }

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
    
    @Inject
    public void setDeploymentSettings(DeploymentSettings deploymentSettings) {
        this.deploymentSettings = deploymentSettings;
    }

}
