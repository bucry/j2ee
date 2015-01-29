package com.framework.core.platform.web.cookie;

import java.util.UUID;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.framework.core.collection.Key;
import com.framework.core.platform.web.site.SiteHelper;
import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author neo
 */
public class CookieInterceptor extends HandlerInterceptorAdapter {

    private static final String ATTRIBUTE_CONTEXT_INITIALIZED = CookieInterceptor.class.getName() + ".CONTEXT_INITIALIZED";
    private static final String CLIENT_ID = "clientId";
    
    private CookieContext cookieContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // only site controller may need cookies
        if (!SiteHelper.isSiteController(handler)) return true;

        Boolean initialized = (Boolean) request.getAttribute(ATTRIBUTE_CONTEXT_INITIALIZED);
        if (!Boolean.TRUE.equals(initialized)) {
            Cookie[] cookies = request.getCookies(); //从客户端获取所有的cookie放入到对象CookieContext中去。
            if (cookies != null)
                for (Cookie cookie : cookies) {
                    cookieContext.addCookie(cookie.getName(), cookie.getValue());
                }
            cookieContext.setHttpServletResponse(response);
            if (cookieContext.getCookie(Key.stringKey(CLIENT_ID)) == null) {
                cookieContext.setCookie(Key.stringKey(CLIENT_ID), UUID.randomUUID().toString(), new CookieSpec(CookieSpec.MAX_AGE_SESSION_SCOPE));
            }
            request.setAttribute(ATTRIBUTE_CONTEXT_INITIALIZED, Boolean.TRUE);
        }
        return true;
    }


    @Inject
    public void setCookieContext(CookieContext cookieContext) {
        this.cookieContext = cookieContext;
    }
}
