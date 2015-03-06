package com.framework.core.platform.web.tag;

public class JsTag extends TagSupport {

    private String src;
    
    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    protected int startTag() throws Exception {
        String[] jses = src.split(",");
        String jsFolder = siteSettings.getJsDir();
        StringBuilder jsBuffer = new StringBuilder();
        for (String js : jses) {
            jsBuffer.append("<script src=\"" + super.constructCDNURL(jsFolder + "/" + js.trim()) + "\"></script>").append("\n");
        }
        pageContext.getOut().print(jsBuffer.toString());
        return SKIP_BODY;
    }

}