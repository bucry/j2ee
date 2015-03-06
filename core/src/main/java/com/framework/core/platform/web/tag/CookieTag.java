package com.framework.core.platform.web.tag;

import com.framework.core.collection.Key;
import com.framework.core.platform.web.cookie.CookieContext;
import com.framework.core.utils.StringUtils;

public class CookieTag extends TagSupport {

    private String key;

    @Override
    protected int startTag() throws Exception {
        if (!StringUtils.hasText(key))
            return SKIP_BODY;
        CookieContext cookieContext = ctx.getBean(CookieContext.class);
        Object context = cookieContext.getCookie(Key.key(key, Object.class));
        pageContext.getOut().print(context == null ? "" : context);
        return SKIP_BODY;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
