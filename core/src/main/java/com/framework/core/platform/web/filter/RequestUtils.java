package com.framework.core.platform.web.filter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import com.framework.core.utils.StringUtils;

public final class RequestUtils {

    public static String getRelativeRequestURLWithQueryString(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(getRelativeRequestURL(request));
        addQueryString(builder, request);
        return builder.toString();
    }
    
    public static String getClientRelativeRequestURLWithQueryString(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(getClientRelativeRequestURL(request));
        addQueryString(builder, request);
        return builder.toString();
    }
    
    public static String getRelativeRequestURL(HttpServletRequest request) {
        String forwardPath = (String) request.getAttribute(RequestDispatcher.FORWARD_PATH_INFO);
        String pathInfo = request.getPathInfo();
        return forwardPath != null ? forwardPath : pathInfo;
    }

    public static String getClientRelativeRequestURL(HttpServletRequest request) {
        String forwardPath = (String) request.getAttribute(RequestDispatcher.FORWARD_PATH_INFO);
        if (forwardPath != null) return forwardPath;
        forwardPath = request.getPathInfo();
        if (forwardPath != null) return forwardPath;
        
        if ("/".equals(request.getContextPath())) return request.getRequestURI();
        return request.getRequestURI().replace(request.getContextPath(), "");
    }

    public static String getRequestURLWithQueryString(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(request.getRequestURL());
        addQueryString(builder, request);
        return builder.toString();
    }

    private static String getQueryString(HttpServletRequest request) {
        String forwardQueryString = (String) request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING);
        if (forwardQueryString != null) return forwardQueryString;
        return request.getQueryString();
    }

    private static void addQueryString(StringBuilder builder, HttpServletRequest request) {
        String queryString = getQueryString(request);
        if (StringUtils.hasText(queryString)) {
            builder.append('?').append(queryString);
        }
    }

    private RequestUtils() {
    }
}