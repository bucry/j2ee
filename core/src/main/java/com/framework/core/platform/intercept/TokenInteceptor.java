package com.framework.core.platform.intercept;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.framework.core.collection.Key;
import com.framework.core.collection.converter.JSONConverter;
import com.framework.core.http.HTTPConstants;
import com.framework.core.platform.web.session.SessionContext;
import com.framework.core.platform.web.site.SiteSettings;
import com.framework.core.platform.web.site.URLBuilder;
import com.framework.core.platform.web.site.security.AuthSettings;
import com.framework.core.platform.web.tag.TokenTag;

public class TokenInteceptor  extends HandlerInterceptorAdapter {
    
    private static final Integer SYNCH_TOKEN_AGE = 30;

    private AuthSettings authSettings;
    
    private SiteSettings siteSettings;
    
    private SessionContext sessionContext;
    
    private JSONConverter jsonConvert;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod && isDuplicateFormSubmission(request)) {
            redirectToTokenExpiredPage(request, response);
            return false; 
        }
        return true;
    }
    
    public boolean isDuplicateFormSubmission(HttpServletRequest request) {
        String token = request.getParameter(TokenTag.SYNCH_TOKEN_INPUT_FIELD_NAME);
        boolean isDuplicate = false;
        if (StringUtils.hasText(token)) {
            HashMap<String, Long> synchTokens = null;
            String mapString = sessionContext.get(Key.stringKey(TokenTag.SYNCH_TOKEN_INPUT_FIELD_NAME));
            if (StringUtils.hasText(mapString)) {
                synchTokens = (HashMap<String, Long>) jsonConvert.fromString(Map.class, mapString);
            }
            if (null == synchTokens) {
                synchTokens = new HashMap<String, Long>();    
            }
            if (!synchTokens.containsKey(token)) {
                long now = System.currentTimeMillis();    //curent time
                long tooOld = now - SYNCH_TOKEN_AGE;      //now -30s
                
                Set<Entry<String, Long>> oldTokens = synchTokens.entrySet();
                Iterator<Entry<String, Long>> iter = oldTokens.iterator();
                while (iter.hasNext()) {
                    Entry<String, Long> oldToken = iter.next();
                    long submitTime = oldToken.getValue();
                    while (submitTime <= tooOld) {
                        iter.remove();
                        break;
                    }
                }
                synchTokens.put(token, now);
            } else {
                isDuplicate = true;
            }
            sessionContext.set(Key.stringKey(TokenTag.SYNCH_TOKEN_INPUT_FIELD_NAME), jsonConvert.toString(synchTokens));
        }
        return isDuplicate;
    }
    
    private void redirectToTokenExpiredPage(HttpServletRequest request, HttpServletResponse response) {
        URLBuilder builder = new URLBuilder();
        builder.setContext(request.getContextPath(), null);
        final String redirectUrl = sessionContext.get(authSettings.getKeyForLoginRedirectDestinationUrl());
        builder.setLogicalURL(StringUtils.hasText(redirectUrl) ? redirectUrl : siteSettings.getOriginalUrl());
        response.setStatus(HTTPConstants.SC_MOVED_TEMPORARILY);
        response.setHeader(HTTPConstants.HEADER_REDIRECT_LOCATION, builder.buildRelativeURL());
    }

    @Inject
    public void setAuthSettings(AuthSettings authSettings) {
        this.authSettings = authSettings;
    }

    @Inject
    public void setSiteSettings(SiteSettings siteSettings) {
        this.siteSettings = siteSettings;
    }
    
    @Inject
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Inject
    public void setJsonConvert(JSONConverter jsonConvert) {
        this.jsonConvert = jsonConvert;
    }

}