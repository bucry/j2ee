package com.framework.core.platform.web.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class HttpDateFormatter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpDateFormatter.class);

    private final SimpleDateFormat httpDateFormat;

    public HttpDateFormatter() {
        httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        httpDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    public synchronized String formatHttpDate(Date date) {
        return httpDateFormat.format(date);
    }

    public synchronized Date parseDateFromHttpDate(String date) {
        try {
            return httpDateFormat.parse(date);
        } catch (ParseException e) {
            LOG.debug("ParseException on date {}. 1/1/1970 will be returned", date);
            return new Date(0);
        }
    }
    
}