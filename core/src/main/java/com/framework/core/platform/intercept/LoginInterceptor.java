package com.framework.core.platform.intercept;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.framework.core.http.HTTPConstants;
import com.framework.core.platform.exception.BusinessProcessException;
import com.framework.core.platform.intercept.LoginRequired.LoginType;
import com.framework.core.platform.web.session.SessionContext;
import com.framework.core.platform.web.site.SiteSettings;
import com.framework.core.platform.web.site.URLBuilder;
import com.framework.core.platform.web.site.security.AuthSettings;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * @author chris
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private SessionContext sessionContext;
    
    private AuthSettings authSettings;
    
    private SiteSettings siteSettings;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            LoginRequired loginRequired = findAnnotation((HandlerMethod) handler, LoginRequired.class);
            if (loginRequired != null && loginRequired.value().equals(LoginType.Vendor)  && !loggedIn()) {
                redirectToVendorLoginPage(request, response); 
                return false;
            } else if (loginRequired != null && !loggedIn()) {
                redirectToLoginPage(request, response);
                return false;
            }
        }
        return true;
    }

    private void redirectToVendorLoginPage(HttpServletRequest request, HttpServletResponse response) {
        final String ajaxHeader = request.getHeader("x-requested-with");
        if (StringUtils.hasText(ajaxHeader) && "XMLHttpRequest".equalsIgnoreCase(ajaxHeader)) {
            throw new BusinessProcessException("session timeout");
        }  else {
            URLBuilder builder = new URLBuilder();
            builder.setContext(request.getContextPath(), null);
            builder.setLogicalURL("/vendor/login");
            response.setStatus(HTTPConstants.SC_MOVED_TEMPORARILY);
            response.setHeader(HTTPConstants.HEADER_REDIRECT_LOCATION, builder.buildRelativeURL());
        }
    }

    private boolean loggedIn() {
        Boolean loggedIn = sessionContext.get(authSettings.getKeyForLoginedIn());
        return Boolean.TRUE.equals(loggedIn);
    }

    private void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response) {
        final String ajaxHeader = request.getHeader("x-requested-with");
        if (StringUtils.hasText(ajaxHeader) && "XMLHttpRequest".equalsIgnoreCase(ajaxHeader)) {
            throw new BusinessProcessException("session timeout");
        }  else {
            URLBuilder builder = new URLBuilder();
            builder.setContext(request.getContextPath(), null);
            builder.setLogicalURL(siteSettings.getLoginUrl());
            response.setStatus(HTTPConstants.SC_MOVED_TEMPORARILY);
            response.setHeader(HTTPConstants.HEADER_REDIRECT_LOCATION, builder.buildRelativeURL());
        }
    }

    private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
    }

    @Inject
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Inject
    public void setAuthSettings(AuthSettings authSettings) {
        this.authSettings = authSettings;
    }
    
    @Inject
    public void setSiteSettings(SiteSettings siteSettings) {
        this.siteSettings = siteSettings;
    }

}