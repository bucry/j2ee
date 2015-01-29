package com.framework.core.platform.web.tag;

public class CssTag extends TagSupport {

    private String href;
    
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    protected int startTag() throws Exception {
        String[] csses = href.split(",");
        String cssFolder = siteSettings.getCssDir();
        StringBuilder cssBuffer = new StringBuilder();
        for (String css : csses) {
            cssBuffer.append("<link href=\"" + super.constructCDNURL(cssFolder + "/" + css.trim()) + "\" rel=\"stylesheet\" />").append("\n");
        }
        pageContext.getOut().print(cssBuffer.toString());
        return SKIP_BODY;
    }
    
}