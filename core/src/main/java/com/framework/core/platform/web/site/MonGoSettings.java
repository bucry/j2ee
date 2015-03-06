package com.framework.core.platform.web.site;

public class MonGoSettings {
    
    private String remoteImageServers;
    
    private boolean autoConnectRetry;
    
    private int perhostConnections;
    
    private int threads;
    
    public String getRemoteImageServers() {
        return remoteImageServers;
    }

    public void setRemoteImageServers(String remoteImageServers) {
        this.remoteImageServers = remoteImageServers;
    }

    public boolean isAutoConnectRetry() {
        return autoConnectRetry;
    }

    public void setAutoConnectRetry(boolean autoConnectRetry) {
        this.autoConnectRetry = autoConnectRetry;
    }

    public int getPerhostConnections() {
        return perhostConnections;
    }

    public void setPerhostConnections(int perhostConnections) {
        this.perhostConnections = perhostConnections;
    }

    public int getThreads() {
        return threads;
    }

    public void setThreads(int threads) {
        this.threads = threads;
    }

}
