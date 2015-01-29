package com.framework.core.platform.web.tag;

public class FstaticTag extends TagSupport {

    @Override
    protected int startTag() throws Exception {
        pageContext.getOut().print(super.constructNFSURL(""));
        return SKIP_BODY;
    }

}