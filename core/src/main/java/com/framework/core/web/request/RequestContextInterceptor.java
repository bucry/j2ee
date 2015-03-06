package com.framework.core.web.request;

import java.util.Date;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.ParameterizableViewController;

import com.framework.core.log.LogConstants;
import com.framework.core.platform.ClassUtils;
import com.framework.core.utils.StringUtils;

public class RequestContextInterceptor extends HandlerInterceptorAdapter {
    public static final String HEADER_REQUEST_ID = "request-id";
    private static final String PARAM_REQUEST_ID = "_requestId";
    private static final String ATTRIBUTE_CONTEXT_INITIALIZED = RequestContextInterceptor.class.getName() + ".CONTEXT_INITIALIZED";

    private final Logger logger = LoggerFactory.getLogger(RequestContextInterceptor.class);
    private RequestContext requestContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Boolean initialized = (Boolean) request.getAttribute(ATTRIBUTE_CONTEXT_INITIALIZED);
        // only process non-forwarded request, to make sure only init once per request
        if (!Boolean.TRUE.equals(initialized)) {
            requestContext.setHTTPRequest(request);
            assignRequestId(request);
            assignRequestDate();
            assignAction(handler);
            request.setAttribute(ATTRIBUTE_CONTEXT_INITIALIZED, Boolean.TRUE);
        }
        return true;
    }

    private void assignRequestDate() {
        Date now = new Date();
        requestContext.setRequestDate(now);
        logger.debug("requestDate={}", now);
    }

    private void assignAction(Object handler) {
        String action = null;
        if (handler instanceof HandlerMethod) {
            action = String.format("%s-%s", ClassUtils.getSimpleOriginalClassName(((HandlerMethod) handler).getBean()), ((HandlerMethod) handler).getMethod().getName());
        } else if (handler instanceof ParameterizableViewController) {
            action = getSimpleViewName(((ParameterizableViewController) handler).getViewName());
        }
        requestContext.setAction(action);
        MDC.put(LogConstants.MDC_ACTION, action);
        logger.debug("requestAction={}", action);
    }

    private void assignRequestId(HttpServletRequest request) {
        String requestId = getRequestId(request);
        RequestIdValidator.validateRequestId(requestId);
        requestContext.setRequestId(requestId);
        MDC.put(LogConstants.MDC_REQUEST_ID, requestId);
        logger.debug("requestId={}", requestId);
    }

    private String getRequestId(HttpServletRequest request) {
        String requestIdFromHeader = request.getHeader(HEADER_REQUEST_ID);
        if (StringUtils.hasText(requestIdFromHeader)) return requestIdFromHeader;
        String requestIdFromParam = request.getParameter(PARAM_REQUEST_ID);
        if (StringUtils.hasText(requestIdFromParam)) return requestIdFromParam;
        logger.debug("request headers do not contain request-id, generate new one");
        return UUID.randomUUID().toString();
    }

    private String getSimpleViewName(String viewName) {
        int index = viewName.indexOf(':');
        if (index > -1) return viewName.substring(index + 1);
        return viewName;
    }

    @Inject
    public void setRequestContext(RequestContext requestContext) {
        this.requestContext = requestContext;
    }

}