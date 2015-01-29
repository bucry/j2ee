package com.framework.core.platform.web.site;

import com.framework.core.platform.web.session.SessionProviderType;
import com.framework.core.utils.TimeLength;

public class SiteSettings {

    private String errorPage;

    private String resourceNotFoundPage;

    private String sessionTimeOutPage;

    private TimeLength sessionTimeOut = TimeLength.minutes(15);
    
    private SessionProviderType sessionProviderType = SessionProviderType.LOCAL;

    private String remoteSessionServers;

    private String originalUrl;
    
    private String loginUrl;
    
    private String version;
    
    private String staticDir;
    
    private String jsDir;
    
    private String cssDir;
    
    private String nfsDir;

    public String getErrorPage() {
        return errorPage;
    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }

    public String getResourceNotFoundPage() {
        return resourceNotFoundPage;
    }

    public void setResourceNotFoundPage(String resourceNotFoundPage) {
        this.resourceNotFoundPage = resourceNotFoundPage;
    }

    public String getSessionTimeOutPage() {
        return sessionTimeOutPage;
    }

    public void setSessionTimeOutPage(String sessionTimeOutPage) {
        this.sessionTimeOutPage = sessionTimeOutPage;
    }

    public TimeLength getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(TimeLength sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public SessionProviderType getSessionProviderType() {
        return sessionProviderType;
    }

    public void setSessionProviderType(SessionProviderType sessionProviderType) {
        this.sessionProviderType = sessionProviderType;
    }

    public String getRemoteSessionServers() {
        return remoteSessionServers;
    }

    public void setRemoteSessionServers(String remoteSessionServers) {
        this.remoteSessionServers = remoteSessionServers;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getJsDir() {
        return jsDir;
    }

    public void setJsDir(String jsDir) {
        this.jsDir = jsDir;
    }

    public String getCssDir() {
        return cssDir;
    }

    public void setCssDir(String cssDir) {
        this.cssDir = cssDir;
    }

    public String getNfsDir() {
        return nfsDir;
    }

    public void setNfsDir(String nfsDir) {
        this.nfsDir = nfsDir;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public String getStaticDir() {
        return staticDir;
    }

    public void setStaticDir(String staticDir) {
        this.staticDir = staticDir;
    }

}