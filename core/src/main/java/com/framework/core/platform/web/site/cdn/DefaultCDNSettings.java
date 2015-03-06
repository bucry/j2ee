package com.framework.core.platform.web.site.cdn;

import com.framework.core.utils.StringUtils;

/**
 * @author neo
 */
public class DefaultCDNSettings implements CDNSettings {
    
    private String[] cdnHosts;
    
    private String[] nfsHosts;

    private String localPath;

    private boolean supportHTTPS;

    private boolean supportS3;
    
    private String bucketName;
    @Override
    public String[] getCDNHosts() {
        return cdnHosts;
    }

    @Override
    public boolean supportHTTPS() {
        return supportHTTPS;
    }
    
    @Override
    public String[] getNFSHosts() {
        // TODO Auto-generated method stub
        return this.nfsHosts;
    }
    
    @Override
    public String getLocalPath() {
        return localPath;
    }
    
    @Override
    public boolean supportS3() {
        return supportS3;
    }

    @Override
    public String getBucketName() {
        return bucketName;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public void setCDNHostsWithCommaDelimitedValue(String cdnHosts) {
        if (StringUtils.hasText(cdnHosts))
            this.cdnHosts = cdnHosts.split(",");
    }

    public void setSupportHTTPS(boolean supportHTTPS) {
        this.supportHTTPS = supportHTTPS;
    }

    public void setNfsHosts(String nfsHosts) {
        if (StringUtils.hasText(nfsHosts))
            this.nfsHosts = nfsHosts.split(",");
    }
   
    public void setSupportS3(boolean supportS3) {
        this.supportS3 = supportS3;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    
}