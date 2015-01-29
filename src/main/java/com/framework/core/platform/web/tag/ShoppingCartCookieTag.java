package com.framework.core.platform.web.tag;

import com.framework.core.collection.Key;
import com.framework.core.platform.web.cookie.CookieContext;

public class ShoppingCartCookieTag extends TagSupport {

    private static final String KEY = "cart_quantity";
    private String content;

    @Override
    protected int startTag() throws Exception {
        CookieContext cookieContext = ctx.getBean(CookieContext.class);
        String cartQty = (String) cookieContext.getCookie(Key.key(KEY, Object.class));
        if (cartQty == null) {
            pageContext.getOut().print("<sup></sup>");
        } else {
            pageContext.getOut().print(String.format(content, cartQty));
        }
        return SKIP_BODY;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
