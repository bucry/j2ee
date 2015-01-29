package com.framework.core.platform.web.tag;

public class StaticTag extends TagSupport {
    
    @Override
    public String constructCDNURL(String url) {
        if (!supportCDN()) return super.constructLocalURL(url, false); //不支持CDN
        String cdnHost = determineCDNHost(url);
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(cdnHost)
                .append(constructServerPort())
                .append(url);
        return builder.toString();
    }
    
    @Override
    protected int startTag() throws Exception {
        pageContext.getOut().print(constructCDNURL(siteSettings.getStaticDir()));
        return SKIP_BODY;
    }
    
}