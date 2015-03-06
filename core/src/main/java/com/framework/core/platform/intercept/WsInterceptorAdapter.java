package com.framework.core.platform.intercept;

import java.lang.annotation.Annotation;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.framework.core.platform.monitor.Pass;

public abstract class WsInterceptorAdapter extends HandlerInterceptorAdapter {
    
    private static final String ATTRIBUTE_CLIENT_KEY = "WS-APPKEY";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Pass pass = findAnnotation((HandlerMethod) handler, Pass.class);
            if (null == pass) {
                validateClient(request.getHeader(ATTRIBUTE_CLIENT_KEY));
            }
        }
        return true;
    }

    private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
    }
    
    protected abstract void validateClient(String appKey);

}