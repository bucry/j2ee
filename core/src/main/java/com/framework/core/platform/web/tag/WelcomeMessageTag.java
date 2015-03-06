package com.framework.core.platform.web.tag;

import java.net.URLDecoder;

import com.framework.core.collection.Key;
import com.framework.core.platform.web.cookie.CookieContext;
import com.framework.core.platform.web.session.SessionContext;
import com.framework.core.utils.StringUtils;

/**
 * 
 */
public class WelcomeMessageTag extends TagSupport {
    private static final String CLIENT_KEY = "cart_client_id";
    private static final String CUSTOMER_KEY = "customer";
    private static final String LOGIN_SESSION_KEY = "session_loginid";
    private String logined;
    private String unLogined;
    private String timeout;

    @Override
    protected int startTag() throws Exception {
        CookieContext cookieContext = ctx.getBean(CookieContext.class);
        SessionContext sessionContext = (SessionContext) ctx.getBean("sessionContext");
        String clientId = cookieContext.getCookie(Key.key(CLIENT_KEY, String.class));
        String customer = cookieContext.getCookie(Key.key(CUSTOMER_KEY, String.class));
        Integer loginId = sessionContext.get(Key.key(LOGIN_SESSION_KEY, Integer.class));
        String content = "";
        if (StringUtils.hasText(customer)) {
            customer = URLDecoder.decode(customer, "UTF-8");
        }
        if (loginId != null && StringUtils.hasText(customer)) {
            content = String.format(logined, customer);
        } else if (loginId == null && StringUtils.hasText(clientId) && StringUtils.hasText(customer)) {
            /* 登录失效 */
            content = String.format(timeout, customer);
        } else {
            /* 用户从未登录 */
            content = String.format(unLogined);
        }
        pageContext.getOut().print(content);
        return SKIP_BODY;
    }

    public String getLogined() {
        return logined;
    }

    public void setLogined(String logined) {
        this.logined = logined;
    }

    public String getUnLogined() {
        return unLogined;
    }

    public void setUnLogined(String unLogined) {
        this.unLogined = unLogined;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

}
