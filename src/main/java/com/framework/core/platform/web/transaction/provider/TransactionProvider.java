package com.framework.core.platform.web.transaction.provider;

import com.framework.core.platform.monitor.ServiceMonitor;

public interface TransactionProvider extends ServiceMonitor {

    String getAndRefreshTransaction(String transactionId);

    void saveTransaction(String transactionId, String transactionData);

    void clearTransaction(String transactionId);

}