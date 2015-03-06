package com.framework.core.platform.web.site;

import com.framework.core.utils.TimeLength;

public class TransactionSettings {
    
    private TimeLength transactionTimeOut = TimeLength.minutes(15);
    
    private String remoteTransactionServers;
    
    public TimeLength getTransactionTimeOut() {
        return transactionTimeOut;
    }

    public void setTransactionTimeOut(TimeLength transactionTimeOut) {
        this.transactionTimeOut = transactionTimeOut;
    }

    public String getRemoteTransactionServers() {
        return remoteTransactionServers;
    }

    public void setRemoteTransactionServers(String remoteTransactionServers) {
        this.remoteTransactionServers = remoteTransactionServers;
    }
}
