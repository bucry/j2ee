package com.framework.core.platform.web.tag;

public class LinkTag extends TagSupport {

    private String src;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    protected int startTag() throws Exception {
        pageContext.getOut().print(super.constructNFSURL(src));
        return SKIP_BODY;
    }
    
}