package com.framework.core.platform.web.tag;

import org.springframework.util.StringUtils;
import com.framework.core.collection.Key;
import com.framework.core.platform.web.site.sso.SsoSettings;
import com.framework.core.platform.web.site.sso.SsoSettings.SsoSite;

public class SsoTag extends TagSupport {

    private String sites;
    
    private String labels;

    public String getSites() {
        return sites;
    }

    public void setSites(String sites) {
        this.sites = sites;
    }
    
    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    private String buildSsoUrl(SsoSite ssoSite) {
        StringBuilder builder = new StringBuilder("https://");
        builder.append(ssoSite.getDns());
        if (!"/".equalsIgnoreCase(ssoSite.getDeployContext()))
            builder.append(ssoSite.getDeployContext());
        if (StringUtils.hasText(ssoSite.getAccessPoint()))
            builder.append(ssoSite.getAccessPoint());
        return builder.toString();
    }

    @Override
    protected int startTag() throws Exception {
        if (!StringUtils.hasText(getSites()) || !StringUtils.hasText(getLabels())) return SKIP_BODY;
        final String[] siteList = sites.split(",");
        final String[] labelList = labels.split(",");
        if (siteList.length != labelList.length)  return SKIP_BODY;
        StringBuilder ssoUrl = new StringBuilder();
        final SsoSettings ssoSettings = ctx.getBean(SsoSettings.class);
        final Class clazz = Class.forName(ssoSettings.getSsoConstantsClassName());
        for (int k = 0; k < siteList.length; k++) {
            Key<SsoSite> siteKey = (Key<SsoSite>) clazz.getField(siteList[k].trim().toUpperCase()).get(null);
            SsoSite ssoSite = ssoSettings.getSsoSites().get(siteKey);
            ssoUrl.append("<li class=\"site-" + siteList[k] + "\"><a href=\"" + buildSsoUrl(ssoSite) + "\">" + labelList[k] + "</a></li>");
        }
        pageContext.getOut().print(ssoUrl.toString());
        return SKIP_BODY;
    }

}