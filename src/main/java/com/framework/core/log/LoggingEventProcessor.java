package com.framework.core.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LoggingEventProcessor {
    private final Queue<ILoggingEvent> events = new ConcurrentLinkedQueue<ILoggingEvent>();
    private boolean hold = true;
    private final Appender<ILoggingEvent> appender;

    public LoggingEventProcessor(Appender<ILoggingEvent> appender) {
        this.appender = appender;
    }

    public void process(ILoggingEvent event) {
        if (hold) {
            event.getThreadName();  // force "logback" to remember current thread
            events.add(event);
            if (event.getLevel().isGreaterOrEqual(Level.ERROR)) {
                hold = false;

                flushTraceLogs();

                events.clear();
            }
        } else {
            appender.doAppend(event);
        }
    }

    public void flushTraceLogs() {
        for (ILoggingEvent logEvent : events) {
            appender.doAppend(logEvent);
        }
    }
}
