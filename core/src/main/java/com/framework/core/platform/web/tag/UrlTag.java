package com.framework.core.platform.web.tag;

public class UrlTag extends TagSupport {
    
    private String action;
    
    private String scheme;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    protected int startTag() throws Exception {
        pageContext.getOut().print(super.constructURL(action, scheme));
        return SKIP_BODY;
    }
}