package com.framework.core.http;

public enum EndPoint {
    
    ACTIVITYSERVER("http://127.0.0.1:8080/RoomServer/hessian/IActivityService"),
    
    ROOMSERVER("http://127.0.0.1:8080/RoomServer/hessian/IRoomService"),
    
    PLAYERSERVER("http://127.0.0.1:8080/RoomServer/hessian/IPlayerService");
    
    private String endpoint;
    
    private static final  String  SERVERPOINT = "";
    
    EndPoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public String getEndpoint() {
        return SERVERPOINT + endpoint;
    }
}