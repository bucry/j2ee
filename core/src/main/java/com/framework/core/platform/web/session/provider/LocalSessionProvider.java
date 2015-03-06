package com.framework.core.platform.web.session.provider;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import com.framework.core.platform.monitor.ServiceStatus;
import com.framework.core.platform.web.site.SiteSettings;
import com.framework.core.utils.DateUtils;

public class LocalSessionProvider implements SessionProvider {

    private static class SessionValue {
        private final Date expiredDate;
        private final String data;

        public SessionValue(Date expiredDate, String data) {
            this.expiredDate = expiredDate;
            this.data = data;
        }

        public Date getExpiredDate() {
            return expiredDate;
        }

        public String getData() {
            return data;
        }
    }

    private final Map<String, SessionValue> values = new ConcurrentHashMap<String, SessionValue>();

    private SiteSettings siteSettings;

    @Override
    public String getServiceName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getAndRefreshSession(String sessionId) {
        SessionValue sessionValue = values.get(sessionId);
        if (sessionValue == null) return null;

        if (new Date().after(sessionValue.getExpiredDate())) {
            values.remove(sessionId);
            return null;
        }

        String data = sessionValue.getData();
        values.put(sessionId, new SessionValue(expirationTime(), data));
        return data;
    }

    @Override
    public void saveSession(String sessionId, String sessionData) {
        values.put(sessionId, new SessionValue(expirationTime(), sessionData));
    }

    @Override
    public void clearSession(String sessionId) {
        values.remove(sessionId);
    }

    private Date expirationTime() {
        return DateUtils.add(new Date(), Calendar.SECOND, (int) siteSettings.getSessionTimeOut().toSeconds());
    }

    @Override
    public ServiceStatus getServiceStatus() throws Exception {
        return ServiceStatus.UP;
    }

    @Inject
    public void setSiteSettings(SiteSettings siteSettings) {
        this.siteSettings = siteSettings;
    }
}