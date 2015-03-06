package com.framework.core.platform.web.tag;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.tags.RequestContextAwareTag;
import com.framework.core.http.HTTPConstants;
import com.framework.core.platform.web.DeploymentEnvironment;
import com.framework.core.platform.web.DeploymentSettings;
import com.framework.core.platform.web.site.SiteSettings;
import com.framework.core.platform.web.site.URLBuilder;
import com.framework.core.platform.web.site.cdn.CDNSettings;
import com.framework.core.platform.web.site.security.AuthSettings;
import com.framework.core.utils.StringUtils;

public abstract class TagSupport extends RequestContextAwareTag {

    protected HttpServletRequest request;

    protected SiteSettings siteSettings;

    protected DeploymentSettings deploymentSettings;

    protected CDNSettings cdnSettings;
    
    protected AuthSettings authSettings;

    protected ApplicationContext ctx;

    protected String scheme;

    protected int doStartTagInternal() throws Exception {
        this.ctx = this.getRequestContext().getWebApplicationContext();
        this.request = (HttpServletRequest) this.pageContext.getRequest();
        this.siteSettings = ctx.getBean(SiteSettings.class);
        this.deploymentSettings = ctx.getBean(DeploymentSettings.class);
        this.cdnSettings = ctx.getBean(CDNSettings.class);
        this.authSettings = ctx.getBean(AuthSettings.class);
        this.scheme = DeploymentEnvironment.DEV.equals(deploymentSettings.getEnvironment()) ? request.getScheme() : request.getHeader("X-Forwarded-Proto");
        return startTag();
    }

    public String constructServerPort() {
        int httpPort = deploymentSettings.getHTTPPort();
        int httpsPort = deploymentSettings.getHTTPSPort();
        if (HTTPConstants.SCHEME_HTTP.equals(scheme)) {
            return httpPort == 80 ? "" : ":" + httpPort;
        }
        return httpsPort == 443 ? "" : ":" + httpsPort;
    }

    public String constructCDNURL(String url) {
        if (!supportCDN()) return constructLocalURL(url, true); // 不支持CDN
        String cdnHost = determineCDNHost(url);
        char connector = url.indexOf('?') < 0 ? '?' : '&';
        StringBuilder builder = new StringBuilder();
        builder.append(scheme).append("://").append(cdnHost).append(constructServerPort()).append(url).append(connector).append("version=").append(siteSettings.getVersion());
        return builder.toString();
    }

    public String constructNFSURL(String url) {
        if (!supportNFSCDN()) return constructLocalURL(url, false); // 不支持CDN
        String nfsHost = determineNFSHost(url);
        StringBuilder builder = new StringBuilder();
        builder.append("http://").append(nfsHost).append("/fstatic").append(url);
        //builder.append(scheme).append("://").append(nfsHost).append(constructServerPort()).append("/fstatic").append(url);
        return builder.toString();
    }

    public String constructURL(String action, String scheme) {
        StringBuilder builder = new StringBuilder();
        builder.append(scheme).append("://").append(request.getServerName()).append(constructServerPort()).append(request.getContextPath()).append(action);
        return builder.toString();
    }

    public boolean supportCDN() {
        String[] cdnHosts = cdnSettings.getCDNHosts();
        if (cdnHosts == null || cdnHosts.length == 0)
            return false;
        if (HTTPConstants.SCHEME_HTTPS.equals(scheme) && !cdnSettings.supportHTTPS())
            return false;
        return true;
    }
    
    public boolean supportNFSCDN() {
        String[] cdnHosts = cdnSettings.getNFSHosts();
        if (cdnHosts == null || cdnHosts.length == 0)
            return false;
        if (HTTPConstants.SCHEME_HTTPS.equals(scheme) && !cdnSettings.supportHTTPS())
            return false;
        return true;
    }

    /**
     * use hash to generate deterministic spread cdn hosts
     * 
     * @param url
     *            the relative url
     * @return cdn host
     */
    public String determineCDNHost(String url) {
        int index = Math.abs(url.hashCode() % cdnSettings.getCDNHosts().length);
        return cdnSettings.getCDNHosts()[index];
    }

    public String determineNFSHost(String url) {
        int index = Math.abs(url.hashCode() % cdnSettings.getNFSHosts().length);
        return cdnSettings.getNFSHosts()[index];
    }

    public String constructLocalURL(String url, boolean hasVersion) {
        URLBuilder builder = new URLBuilder();
        builder.setContext(request.getContextPath(), deploymentSettings.getDeploymentContext());
        if (hasVersion) builder.setSiteSettings(siteSettings);
        return builder.constructRelativeURL(url);
    }

    public Integer[] compress(String fullSize, Integer width, Integer height) throws IOException {
        if (!StringUtils.hasText(fullSize))
            return new Integer[] {width, height};
        String[] size = fullSize.split(",");
        if (size.length != 2)
            return new Integer[] {width, height};
        int sourceWidth = Integer.parseInt(size[0]);
        int sourceHeight = Integer.parseInt(size[1]);
        if (null == width && null != height) {
            return new Integer[] { (int) Math.ceil(height * sourceWidth * 1.0 / sourceHeight), height };
        } else if (null != width && null == height) {
            return new Integer[] { width, (int) Math.ceil(width * sourceHeight * 1.0 / sourceWidth) };
        } else if (null == width && null == height) {
            return new Integer[] { sourceWidth, sourceHeight };
        } else if (null != width && null != height) {
            if (sourceWidth < width && sourceHeight < height)
                return new Integer[] {sourceWidth, sourceHeight};
            double rate1 = (width * 1.0) / (sourceWidth * 1.0);
            double rate2 = (height * 1.0) / (sourceHeight * 1.0);
            double rate = rate1 > rate2 ? rate2 : rate1;
            int realWidth = (int) Math.ceil(sourceWidth * rate);
            int realHeight = (int) Math.ceil(sourceHeight * rate);
            return new Integer[] { realWidth, realHeight };
        }
        return new Integer[] { sourceWidth, sourceHeight };
    }

    protected abstract int startTag() throws Exception;
}