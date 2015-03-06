package com.framework.core.platform.web;

public class DeploymentSettings {
    
    private DeploymentEnvironment environment = DeploymentEnvironment.DEV;
    private String deploymentContext;
    private String host;
    private int httpPort = 80;
    private int httpsPort = 443;

    public DeploymentEnvironment getEnvironment() {
        return environment;
    }

    public void setEnvironment(DeploymentEnvironment environment) {
        this.environment = environment;
    }

    public String getDeploymentContext() {
        return deploymentContext;
    }

    public void setDeploymentContext(String deploymentContext) {
        this.deploymentContext = deploymentContext;
    }

    public int getHTTPPort() {
        return httpPort;
    }

    public void setHTTPPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public int getHTTPSPort() {
        return httpsPort;
    }

    public void setHTTPSPort(int httpsPort) {
        this.httpsPort = httpsPort;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

}
