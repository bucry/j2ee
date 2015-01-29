package com.framework.core.platform.web.cache;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Properties;
import java.util.TreeSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.spring.MemcachedClientFactoryBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.framework.core.collection.Key;
import com.framework.core.platform.web.cookie.CookieSpec;
import com.framework.core.utils.TimeLength;

public abstract class MemcachedPageFilter implements Filter {
   
    private static final Logger LOG = LoggerFactory.getLogger(PageInfo.class);
    
    protected static final int DELETE_COOKIE_MAX_AGE = 0;
    
    private MemcachedClient memcachedClient;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //创建Memcached连接 
        MemcachedClientFactoryBean factory = new MemcachedClientFactoryBean();
        try {
            factory.setServers(getSettings().getProperty("site.cache.endpoints"));
            factory.setOpTimeout(TimeLength.seconds(3).toMilliseconds());
            factory.setProtocol(ConnectionFactoryBuilder.Protocol.BINARY);
            memcachedClient = (MemcachedClient) factory.getObject();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        PageInfo pageInfo = buildPageInfo(httpRequest, httpResponse, chain);
        if (pageInfo.isOk()) {
            writeResponse(httpRequest, httpResponse, pageInfo);
        }
    }
    
    private void writeResponse(final HttpServletRequest request, final HttpServletResponse response, final PageInfo pageInfo) throws ResponseHeadersNotModifiableException, IOException  {
        setStatus(response, pageInfo);
        setContentType(response, pageInfo);
        setHeaders(pageInfo, response);
        writeContent(request, response, pageInfo);
    }

    private void writeContent(final HttpServletRequest request, final HttpServletResponse response, final PageInfo pageInfo) throws IOException, ResponseHeadersNotModifiableException {
        byte[] body;
        boolean shouldBodyBeZero = ResponseUtil.shouldBodyBeZero(request, pageInfo.getStatusCode());
        if (shouldBodyBeZero) {
            body = new byte[0];
        } else if (acceptsGzipEncoding(request)) {
            body = pageInfo.getGzippedBody();
            if (ResponseUtil.shouldGzippedBodyBeZero(body, request)) {
                body = new byte[0];
            } else {
                ResponseUtil.addGzipHeader(response);
            }
        } else {
            body = pageInfo.getUngzippedBody();
        }
        response.setContentLength(body.length);
        OutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(body);
        out.flush();
    }

    private boolean acceptsGzipEncoding(HttpServletRequest request) {
        return headerContains(request, "Accept-Encoding", "gzip");
    }
    
    private boolean headerContains(final HttpServletRequest request, final String header, final String value) {
        final Enumeration accepted = request.getHeaders(header);
        while (accepted.hasMoreElements()) {
            final String headerValue = (String) accepted.nextElement();
            if (headerValue.indexOf(value) != -1) {
                return true;
            }
        }
        return false;
    }
    
    //do not cache the cookies!
    private PageInfo buildPageInfo(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain) throws IOException, ServletException {
        final String cacheKey = getCacheKey(request);
        PageInfo pageInfo = null;
        Object cacheData = memcachedClient.get(cacheKey);
        if (cacheData == null) {
            final ByteArrayOutputStream outstr = new ByteArrayOutputStream();
            final GenericResponseWrapper wrapper = new GenericResponseWrapper(response, outstr);
            chain.doFilter(request, wrapper);
            wrapper.flush();

            pageInfo =  new PageInfo(wrapper.getStatus(), wrapper.getContentType(), outstr.toByteArray(), true, wrapper.getAllHeaders());
            
            if (pageInfo.isOk()) {
                memcachedClient.set(cacheKey, (int) TimeLength.seconds(30).toSeconds(), pageInfo);
            } 
            
        } else
            pageInfo = (PageInfo) cacheData;
        return pageInfo;
    }

    private void setContentType(final HttpServletResponse response, final PageInfo pageInfo) {
        String contentType = pageInfo.getContentType();
        if (contentType != null && contentType.length() > 0) {
            response.setContentType(contentType);
        }
    }
        
    public <T> void deleteCookie(Key<T> cookieKey, CookieSpec cookieSpec, final HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie(cookieKey.name(), null);
        cookie.setMaxAge(DELETE_COOKIE_MAX_AGE);
        cookie.setPath(cookieSpec.getPath());
        String domain = cookieSpec.getDomain();
        if (StringUtils.hasText(domain)) cookie.setDomain(domain);
        httpServletResponse.addCookie(cookie);
    }

    private void setStatus(final HttpServletResponse response, final PageInfo pageInfo) {
        response.setStatus(pageInfo.getStatusCode());
    }
    
    private void setHeaders(final PageInfo pageInfo, final HttpServletResponse response) {
        final Collection<Header<? extends Serializable>> headers = pageInfo.getHeaders();
        final TreeSet<String> setHeaders = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
        for (final Header<? extends Serializable> header : headers) {
            final String name = header.getName();
            switch (header.getType()) {
                case STRING:
                    if (setHeaders.contains(name)) {
                        response.addHeader(name, (String) header.getValue());
                    } else {
                        setHeaders.add(name);
                        response.setHeader(name, (String) header.getValue());
                    }
                    break;
                case DATE:
                    if (setHeaders.contains(name)) {
                        response.addDateHeader(name, (Long) header.getValue());
                    } else {
                        setHeaders.add(name);
                        response.setDateHeader(name, (Long) header.getValue());
                    }
                    break;
                case INT:
                    if (setHeaders.contains(name)) {
                        response.addIntHeader(name, (Integer) header.getValue());
                    } else {
                        setHeaders.add(name);
                        response.setIntHeader(name, (Integer) header.getValue());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("No mapping for Header: " + header);
            }
        }
    }

    private String getCacheKey(HttpServletRequest httpRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(httpRequest.getMethod()).append(httpRequest.getRequestURI()).append(httpRequest.getQueryString());
        return stringBuffer.toString();
    }
    
    @Override
    public void destroy() {
        memcachedClient.shutdown();
    }
    
    public abstract Properties getSettings() throws IOException;
}