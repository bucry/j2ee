package com.framework.core.platform.web.site.cdn;

/**
 * @author neo
 */
public interface CDNSettings {
    
    String[] getCDNHosts();
    
    String[] getNFSHosts();
    
    String getLocalPath();

    boolean supportHTTPS();
    
    boolean supportS3();
    
    String getBucketName();
}
