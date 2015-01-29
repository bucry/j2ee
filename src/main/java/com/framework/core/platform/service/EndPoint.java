package com.framework.core.platform.service;

public enum EndPoint {
    
	BUCRYWS("http://127.0.0.1:8080/WebServer/"),
	
    CUSTOMERCARE("http://192.168.1.115/customercare"),
    
    SYSTEM("http://192.168.1.115:8080/system"),
    
    SECURITY("http://192.168.1.115/security"), 
    
    PRODUCT("http://192.168.1.115/product"),
    
    ORDER("http://192.168.1.115/order"),
    
    WMS("http://192.168.1.115/wms"), 
    
    VENDOR("http://192.168.1.115/vendor"),
    
    REPORT("http://192.168.1.115/report"),
    
    DROPSHIP("http://192.168.1.115/dropship");
    
    private String endpoint;
    
    private static final  String  SERVERPOINT = "";
    
    EndPoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public String getEndpoint() {
        return SERVERPOINT + endpoint;
    }
}