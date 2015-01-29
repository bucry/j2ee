package com.framework.core.platform.concurrent;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.framework.core.log.TraceLogger;

/**
 * @author neo
 */
public class TaskProxy<T> implements Callable<T> {
    private final Logger logger = LoggerFactory.getLogger(TaskProxy.class);

    private final Callable<T> delegate;
    private final long parentThreadId;

    public TaskProxy(Callable<T> delegate) {
        this.delegate = delegate;
        parentThreadId = Thread.currentThread().getId();
    }

    @Override
    public T call() throws Exception {
        MDC.put(TraceLogger.MDC_TARGET_THREAD_ID, String.valueOf(parentThreadId));
        try {
            logger.debug("start task, task={}, currentThread={}", this, Thread.currentThread().getId());
            return delegate.call();
        } finally {
            logger.debug("end task, task={}, currentThread={}", this, Thread.currentThread().getId());
            MDC.remove(TraceLogger.MDC_TARGET_THREAD_ID);
        }
    }
}
