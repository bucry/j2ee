package com.framework.core.platform;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

import com.framework.core.platform.web.DeploymentSettings;
import com.framework.core.platform.web.cookie.CookieContext;
import com.framework.core.platform.web.cookie.CookieInterceptor;
import com.framework.core.platform.web.request.RequestContext;
import com.framework.core.platform.web.request.RequestContextInterceptor;
import com.framework.core.platform.web.session.SecureSessionContext;
import com.framework.core.platform.web.session.SessionContext;
import com.framework.core.platform.web.session.SessionInterceptor;
import com.framework.core.platform.web.site.LockSettings;
import com.framework.core.platform.web.site.SiteSettings;
import com.framework.core.platform.web.site.TransactionSettings;
import com.framework.core.platform.web.site.scheme.HTTPSchemeEnforceInterceptor;
import com.framework.core.platform.web.site.sso.SsoSettings;

public class DefaultSiteWebConfig extends DefaultWebConfig {

    @Bean
    public DeploymentSettings deploymentSettings() {
        return new DeploymentSettings();
    }

    @Bean
    public SiteSettings siteSettings() {
        return new SiteSettings();
    }
    
    @Bean
    public LockSettings lockSettings() {
        return new LockSettings(); 
    }
    
    @Bean
    public TransactionSettings transactionSettings() {
        return new TransactionSettings();
    }
    
    @Bean
    public SsoSettings ssoSettings() {
        return new SsoSettings();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CookieContext cookieContext() {
        return new CookieContext();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public RequestContext requestContext() {
        return new RequestContext();
    }

    //@Bean
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SessionContext sessionContext() {
        return new SessionContext();
    }

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SecureSessionContext secureSessionContext() {
        return new SecureSessionContext();
    }
   
    @Bean
    public CookieInterceptor cookieInterceptor() {
        return new CookieInterceptor();
    }

    @Bean
    public RequestContextInterceptor requestContextInterceptor() {
        return new RequestContextInterceptor();
    }

    @Bean
    public SessionInterceptor sessionInterceptor() {
        return new SessionInterceptor();
    }

    @Bean
    public HTTPSchemeEnforceInterceptor httpSchemeEnforceInterceptor() {
        return new HTTPSchemeEnforceInterceptor();
    }
}