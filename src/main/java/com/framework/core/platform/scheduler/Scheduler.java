package com.framework.core.platform.scheduler;

import com.framework.core.utils.TimeLength;

import java.util.Date;

/**
 * @author neo
 */
public interface Scheduler {
    void triggerOnce(Job job);

    void triggerOnceAt(Job job, Date time);

    void triggerOnceWithDelay(Job job, TimeLength delay);
}
