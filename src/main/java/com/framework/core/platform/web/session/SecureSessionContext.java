package com.framework.core.platform.web.session;

public class SecureSessionContext extends SessionContext {
    
    protected boolean underSecureRequest;

    void underSecureRequest() {
        underSecureRequest = true;
    }

    public boolean isUnderSecureRequest() {
        return underSecureRequest;
    }

}