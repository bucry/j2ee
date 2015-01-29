package com.framework.core.platform.web.site.scheme;

import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.framework.core.http.HTTPConstants;
import com.framework.core.platform.web.DeploymentSettings;
import com.framework.core.platform.web.filter.RequestUtils;
import com.framework.core.platform.web.request.RequestContext;
import com.framework.core.platform.web.site.URLBuilder;

public class HTTPSchemeEnforceInterceptor extends HandlerInterceptorAdapter {

    private DeploymentSettings deploymentSettings;
    
    private RequestContext  requestContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HTTPOnly httpOnly = findAnnotation((HandlerMethod) handler, HTTPOnly.class);
            if (httpOnly != null && !HTTPConstants.SCHEME_HTTP.equals(requestContext.getScheme())) {
                enforceScheme(request, response, HTTPConstants.SCHEME_HTTP);
                return false;
            }
        
            HTTPSOnly httpsOnly = findAnnotation((HandlerMethod) handler, HTTPSOnly.class);
            if (httpsOnly != null && !HTTPConstants.SCHEME_HTTPS.equals(requestContext.getScheme())) {
                enforceScheme(request, response, HTTPConstants.SCHEME_HTTPS);
                return false;
            }
        }
        return true;
    }

    private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
    }

    private void enforceScheme(HttpServletRequest request, HttpServletResponse response, String scheme) {
        URLBuilder builder = new URLBuilder();
        builder.setContext(request.getContextPath(), deploymentSettings.getDeploymentContext());
        builder.setServerInfo(requestContext.getServerName(), deploymentSettings.getHTTPPort(), deploymentSettings.getHTTPSPort());
        //builder.setServerInfo(request.getServerName(), deploymentSettings.getHTTPPort(), deploymentSettings.getHTTPSPort());
        response.setStatus(HTTPConstants.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", builder.constructAbsoluteURL(scheme, RequestUtils.getClientRelativeRequestURLWithQueryString(request)));
    }

    @Inject
    public void setDeploymentSettings(DeploymentSettings deploymentSettings) {
        this.deploymentSettings = deploymentSettings;
    }

    @Inject
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }
    
}