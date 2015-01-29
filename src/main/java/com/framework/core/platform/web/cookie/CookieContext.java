package com.framework.core.platform.web.cookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.framework.core.collection.Key;
import com.framework.core.collection.KeyMap;
import com.framework.core.collection.TypeConversionException;
import com.framework.core.collection.TypeConverter;
import com.framework.core.utils.AssertUtils;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author neo
 */
public class CookieContext {
    private static final int DELETE_COOKIE_MAX_AGE = 0;
    private final Logger logger = LoggerFactory.getLogger(CookieContext.class);

    private HttpServletResponse httpServletResponse;

    private final KeyMap cookies = new KeyMap();
    private final TypeConverter typeConverter = new TypeConverter();

    public <T> T getCookie(Key<T> cookieKey) {
        try {
            return cookies.get(cookieKey);
        } catch (TypeConversionException e) {
            logger.warn("failed to convert cookie value", e);
            return null;
        }
    }

    public <T> void setCookie(Key<T> cookieKey, T value, CookieSpec cookieSpec) {
        
        Cookie cookie = new Cookie(cookieKey.name(), typeConverter.toString(value));
        if (cookieSpec.getHttpOnly() != null)
            cookie.setHttpOnly(cookieSpec.getHttpOnly());
        cookie.setPath(cookieSpec.getPath());
        if (cookieSpec.getSecure() != null)
            cookie.setSecure(cookieSpec.getSecure());
        if (cookieSpec.getMaxAge() != null)
            cookie.setMaxAge((int) cookieSpec.getMaxAge().toSeconds());
        if (cookieSpec.getDomain() != null)
            cookie.setDomain(cookieSpec.getDomain());

        AssertUtils.assertNotNull(httpServletResponse, "response is not injected, please check cookieInterceptor is added in WebConfig");
        httpServletResponse.addCookie(cookie);
    }

    public <T> void deleteCookie(Key<T> cookieKey, CookieSpec cookieSpec) {
        Cookie cookie = new Cookie(cookieKey.name(), null);
        cookie.setMaxAge(DELETE_COOKIE_MAX_AGE);
        cookie.setPath(cookieSpec.getPath());
        String domain = cookieSpec.getDomain();
        if (StringUtils.hasText(domain)) cookie.setDomain(domain);
        
        AssertUtils.assertNotNull(httpServletResponse, "response is not injected, please check cookieInterceptor is added in WebConfig");
        httpServletResponse.addCookie(cookie);
    }

    void addCookie(String name, String value) {
        cookies.putString(name, value);
    }

    void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }
}
