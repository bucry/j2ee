package com.framework.core.platform.monitor;

import com.framework.core.utils.Convert;
import com.framework.core.xml.XMLBuilder;

import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author neo
 */
public class ExceptionStatistic {
    private long totalCount = 0;
    private final Map<String, Long> exceptionCounts = new HashMap<String, Long>();
    private final Deque<ExceptionInfo> recentExceptions = new LinkedList<ExceptionInfo>();

    private int maxRecentExceptions = 100;

    public void failed(Exception e) {
        String exceptionClass = e.getClass().getName();

        totalCount++;

        synchronized (exceptionCounts) {
            Long count = exceptionCounts.get(exceptionClass);
            if (count == null) {
                count = 0l;
            }
            exceptionCounts.put(exceptionClass, count + 1);
        }

        synchronized (recentExceptions) {
            ExceptionInfo exceptionInfo = new ExceptionInfo();
            exceptionInfo.setExceptionClass(exceptionClass);
            exceptionInfo.setExceptionMessage(e.getMessage());
            exceptionInfo.setOccurred(new Date());
            recentExceptions.add(exceptionInfo);

            while (recentExceptions.size() > maxRecentExceptions) {
                recentExceptions.removeFirst();
            }
        }
    }

    public String toXML() {
        XMLBuilder builder = XMLBuilder.indentedXMLBuilder();
        builder.startElement("exceptionStatistic");
        builder.textElement("totalCount", String.valueOf(totalCount));

        builder.startElement("counts");
        synchronized (exceptionCounts) {
            for (Map.Entry<String, Long> entry : exceptionCounts.entrySet()) {
                builder.startElement("count");
                builder.attribute("class", entry.getKey());
                builder.text(String.valueOf(entry.getValue()));
                builder.endElement();
            }
        }
        builder.endElement();   // end of counts

        builder.startElement("recentExceptions");
        synchronized (recentExceptions) {
            for (ExceptionInfo exceptionInfo : recentExceptions) {
                builder.startElement("exception");
                builder.textElement("class", exceptionInfo.getExceptionClass());
                builder.textElement("message", exceptionInfo.getExceptionMessage());
                builder.textElement("occurred", Convert.toString(exceptionInfo.getOccurred(), Convert.DATE_FORMAT_DATETIME));
                builder.endElement();
            }
        }
        builder.endElement();   // end of recentExceptions

        builder.endElement();   // end of exceptionStatistic
        return builder.toXML();
    }

    public void setMaxRecentExceptions(int maxRecentExceptions) {
        this.maxRecentExceptions = maxRecentExceptions;
    }
}
