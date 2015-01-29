package com.framework.core.platform.web.session.provider;

import com.framework.core.platform.monitor.ServiceMonitor;

public interface SessionProvider extends ServiceMonitor {

    String getAndRefreshSession(String sessionId);

    void saveSession(String sessionId, String sessionData);

    void clearSession(String sessionId);
}
