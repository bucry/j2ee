package com.framework.core.platform;

public enum EndPoint {
    
    BUCRYWS("http://127.0.0.1:8080/WebServer/");
    
    private String endpoint;
    
    private static final  String  SERVERPOINT = "";
    
    EndPoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public String getEndpoint() {
        return SERVERPOINT + endpoint;
    }
}