package com.framework.core.platform.web.site.security;

import com.framework.core.collection.Key;

public class AuthSettings {
    
    private Key<Boolean> keyForLoginedIn;
    
    private Key<String> keyForLoginedUser;
    
    private Key<String> keyForAppkey;
    
    private Key<String> keyForPermissions;
        
    private Key<String> keyForMenus;
    
    private Key<String> keyForLoginRedirectDestinationUrl;
    
    private Key<String> keyForLoginUrl;
    
    private Key<String> keyForTransaction;

    public Key<Boolean> getKeyForLoginedIn() {
        return keyForLoginedIn;
    }

    public void setKeyForLoginedIn(Key<Boolean> keyForLoginedIn) {
        this.keyForLoginedIn = keyForLoginedIn;
    }

    public Key<String> getKeyForLoginedUser() {
        return keyForLoginedUser;
    }

    public void setKeyForLoginedUser(Key<String> keyForLoginedUser) {
        this.keyForLoginedUser = keyForLoginedUser;
    }

    public Key<String> getKeyForAppkey() {
        return keyForAppkey;
    }

    public void setKeyForAppkey(Key<String> keyForAppkey) {
        this.keyForAppkey = keyForAppkey;
    }

    public Key<String> getKeyForPermissions() {
        return keyForPermissions;
    }

    public void setKeyForPermissions(Key<String> keyForPermissions) {
        this.keyForPermissions = keyForPermissions;
    }

    public Key<String> getKeyForMenus() {
        return keyForMenus;
    }

    public void setKeyForMenus(Key<String> keyForMenus) {
        this.keyForMenus = keyForMenus;
    }

    public Key<String> getKeyForLoginRedirectDestinationUrl() {
        return keyForLoginRedirectDestinationUrl;
    }

    public void setKeyForLoginRedirectDestinationUrl(Key<String> keyForLoginRedirectDestinationUrl) {
        this.keyForLoginRedirectDestinationUrl = keyForLoginRedirectDestinationUrl;
    }

    public Key<String> getKeyForLoginUrl() {
        return keyForLoginUrl;
    }

    public void setKeyForLoginUrl(Key<String> keyForLoginUrl) {
        this.keyForLoginUrl = keyForLoginUrl;
    }

    public Key<String> getKeyForTransaction() {
        return keyForTransaction;
    }

    public void setKeyForTransaction(Key<String> keyForTransaction) {
        this.keyForTransaction = keyForTransaction;
    }
}