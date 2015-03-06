package com.framework.core.web.intercept;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.framework.core.exception.BusinessProcessException;
import com.framework.core.http.HTTPConstants;

/**
 * @author bfc
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            LoginRequired loginRequired = findAnnotation((HandlerMethod) handler, LoginRequired.class);
            if (loginRequired != null) {
            	if (request.getSession().getAttribute("userId") == null) {
            		request.getRequestDispatcher("/admin/tologinpage").forward(request, response);
            		return false;
            	}
            } 
        }
        return true;
    }


    private boolean loggedIn() {
        //Boolean loggedIn = sessionContext.get(null);
        //return Boolean.TRUE.equals(loggedIn);
    	return true;
    }

    private void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response) {
        final String ajaxHeader = request.getHeader("x-requested-with");
        if (StringUtils.hasText(ajaxHeader) && "XMLHttpRequest".equalsIgnoreCase(ajaxHeader)) {
            throw new BusinessProcessException("session timeout");
        }  else {
            response.setStatus(HTTPConstants.SC_MOVED_TEMPORARILY);
        }
    }

    private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
    }
}