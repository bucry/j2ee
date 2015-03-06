package com.framework.core.platform.web.session;

import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.framework.core.collection.Key;
import com.framework.core.platform.SpringObjectFactory;
import com.framework.core.platform.web.cookie.CookieContext;
import com.framework.core.platform.web.cookie.CookieSpec;
import com.framework.core.platform.web.session.provider.LocalSessionProvider;
import com.framework.core.platform.web.session.provider.MemcachedSessionProvider;
import com.framework.core.platform.web.session.provider.SessionProvider;
import com.framework.core.platform.web.site.SiteHelper;
import com.framework.core.platform.web.site.SiteSettings;
import com.framework.core.platform.web.site.sso.SsoSettings;
import com.framework.core.utils.AssertUtils;

public class SessionInterceptor extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    private static final Key<String> COOKIE_SESSION_ID = Key.stringKey("SessionId");
    private static final Key<String> COOKIE_SECURE_SESSION_ID = Key.stringKey("SecureSessionId");
    private static final String SESSION_COOKIE_PATH = "/";
    private static final String ATTRIBUTE_CONTEXT_INITIALIZED = SessionInterceptor.class.getName() + ".CONTEXT_INITIALIZED";
    private static final String BEAN_NAME_SESSION_PROVIDER = "sessionProvider";

    private CookieContext cookieContext;
    private SessionContext sessionContext;
    private SecureSessionContext secureSessionContext;
    private SiteSettings siteSettings;
    private SessionProvider sessionProvider;
    private SpringObjectFactory springObjectFactory;
    private SsoSettings ssoSettings;

    @PostConstruct
    public void initialize() {
        SessionProviderType type = siteSettings.getSessionProviderType();
        if (SessionProviderType.MEMCACHED.equals(type)) {
            AssertUtils.assertHasText(siteSettings.getRemoteSessionServers(), "remote session server configuration is missing");
            springObjectFactory.registerSingletonBean(BEAN_NAME_SESSION_PROVIDER, MemcachedSessionProvider.class);
        } else if (SessionProviderType.LOCAL.equals(type)) {
            springObjectFactory.registerSingletonBean(BEAN_NAME_SESSION_PROVIDER, LocalSessionProvider.class);
        } else {
            throw new IllegalStateException("unsupported session provider type, type=" + type);
        }
        sessionProvider = springObjectFactory.getBean(SessionProvider.class);
    }
    
    /**
     * 1.如果我是一个老用户那么就刷新session，获取当前用户Data
     * 2.如果我是一个新用户那么就告诉context我是一个新用户
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // only site controller may need session
        if (!SiteHelper.isSiteController(handler)) return true;

        Boolean initialized = (Boolean) request.getAttribute(ATTRIBUTE_CONTEXT_INITIALIZED);
        
        // only process non-forwarded request, to make sure only init once per request
        if (!Boolean.TRUE.equals(initialized)) {
            loadSession(sessionContext, COOKIE_SESSION_ID);

            if (request.isSecure()) {
                secureSessionContext.underSecureRequest();
                loadSession(secureSessionContext, COOKIE_SECURE_SESSION_ID);
            }

            request.setAttribute(ATTRIBUTE_CONTEXT_INITIALIZED, Boolean.TRUE);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        saveAllSessions(request);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // if some interceptor break the preHandle by returning false, all postHandle will be skipped.
        // by this way we want to try to save session on completion if possible
        // due to setCookies only works before view is rendered
        saveAllSessions(request);
    }

    private void saveAllSessions(HttpServletRequest request) {
        saveSession(sessionContext, COOKIE_SESSION_ID, false);
        if (request.isSecure()) {
            saveSession(secureSessionContext, COOKIE_SECURE_SESSION_ID, true);
        }
    }

    private void loadSession(SessionContext sessionContext, Key<String> sessionIdCookieKey) {
        String sessionId = cookieContext.getCookie(sessionIdCookieKey); //从cookie中跟据key获取sessionid
        if (sessionId != null) {
            String sessionData = sessionProvider.getAndRefreshSession(sessionId);
            if (sessionData != null) {
                sessionContext.setId(sessionId);
                sessionContext.loadSessionData(sessionData);
            } else {
                logger.debug("can not find session, generate new sessionId to replace old one");
                sessionContext.requireNewSessionId();
            }
        }
    }

    private void saveSession(SessionContext sessionContext, Key<String> sessionIdCookieKey, boolean secure) {
        if (sessionContext.changed()) {
            if (sessionContext.invalidated()) {
                deleteSession(sessionContext, sessionIdCookieKey);
            } else {
                persistSession(sessionContext, sessionIdCookieKey, secure);
            }
            sessionContext.saved();
        }
    }

    private void deleteSession(SessionContext sessionContext, Key<String> sessionIdCookieKey) {
        String sessionId = sessionContext.getId();
        if (sessionId == null) {
            // session was not persisted, nothing is required
            return;
        }
        sessionProvider.clearSession(sessionId);
        CookieSpec spec = new CookieSpec();
        String domain = ssoSettings.getCookieDomain();
        if (StringUtils.hasText(domain)) spec.setDomain(domain);
        spec.setPath(SESSION_COOKIE_PATH);
        cookieContext.deleteCookie(sessionIdCookieKey, spec);
    }

    private void persistSession(final SessionContext sessionContext, Key<String> sessionIdCookieKey, boolean secure) {
        String sessionId = sessionContext.getId();
        if (sessionId == null) {
            sessionId = UUID.randomUUID().toString();
            sessionContext.setId(sessionId);
            CookieSpec spec = new CookieSpec();
            spec.setHttpOnly(true);
            spec.setPath(SESSION_COOKIE_PATH);
            spec.setMaxAge(CookieSpec.MAX_AGE_SESSION_SCOPE);
            spec.setSecure(secure);
            String domain = ssoSettings.getCookieDomain();
            if (StringUtils.hasText(domain)) spec.setDomain(domain);
            cookieContext.setCookie(sessionIdCookieKey, sessionId, spec);
        }
        sessionProvider.saveSession(sessionId, sessionContext.getSessionData());
    }

    @Inject
    public void setSessionContext(SessionContext sessionContext) {
        this.sessionContext = sessionContext;
    }

    @Inject
    public void setSecureSessionContext(SecureSessionContext secureSessionContext) {
        this.secureSessionContext = secureSessionContext;
    }

    @Inject
    public void setSiteSettings(SiteSettings siteSettings) {
        this.siteSettings = siteSettings;
    }

    @Inject
    public void setCookieContext(CookieContext cookieContext) {
        this.cookieContext = cookieContext;
    }

    @Inject
    public void setSpringObjectFactory(SpringObjectFactory springObjectFactory) {
        this.springObjectFactory = springObjectFactory;
    }
    
    @Inject
    public void setSsoSettings(SsoSettings ssoSettings) {
        this.ssoSettings = ssoSettings;
    }
 
}