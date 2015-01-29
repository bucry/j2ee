package com.framework.core.platform.web.site.sso;

import java.util.HashMap;
import java.util.Map;
import com.framework.core.collection.Key;

public class SsoSettings {
    
    private String ssoConstantsClassName;

    private String globalDns;

    private String cookieDomain;

    private String cookiePath;

    private final Map<Key<SsoSite>, SsoSite> ssoSites = new HashMap<Key<SsoSite>, SsoSite>();

    public static class SsoSite {

        private final String dns;

        private final String deployContext;

        private String accessPoint;

        public SsoSite(String dns, String deployContext, String accessPoint) {
            this.dns = dns;
            this.deployContext = deployContext;
            this.accessPoint = accessPoint;
        }

        public String getDns() {
            return dns;
        }

        public String getDeployContext() {
            return deployContext;
        }

        public String getAccessPoint() {
            return accessPoint;
        }

        public void setAccessPoint(String accessPoint) {
            this.accessPoint = accessPoint;
        }
    }

    public Map<Key<SsoSite>, SsoSite> getSsoSites() {
        return ssoSites;
    }

    public String getGlobalDns() {
        return globalDns;
    }

    public void setGlobalDns(String globalDns) {
        this.globalDns = globalDns;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getCookiePath() {
        return cookiePath;
    }

    public void setCookiePath(String cookiePath) {
        this.cookiePath = cookiePath;
    }

    public String getSsoConstantsClassName() {
        return ssoConstantsClassName;
    }

    public void setSsoConstantsClassName(String ssoConstantsClassName) {
        this.ssoConstantsClassName = ssoConstantsClassName;
    }

}