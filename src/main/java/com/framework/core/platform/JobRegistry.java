package com.framework.core.platform;

import com.framework.core.platform.scheduler.Job;
import com.framework.core.platform.scheduler.JobExecutor;
import com.framework.core.utils.AssertUtils;
import com.framework.core.utils.DateUtils;
import com.framework.core.utils.TimeLength;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.Calendar;
import java.util.Date;

/**
 * @author neo
 */
public class JobRegistry {
    private final ScheduledTaskRegistrar schedulerRegistry;
    private final DefaultSchedulerConfig config;

    public JobRegistry(ScheduledTaskRegistrar schedulerRegistry, DefaultSchedulerConfig config) {
        this.schedulerRegistry = schedulerRegistry;
        this.config = config;
    }

    public void triggerWithFixedDelay(Class<? extends Job> jobClass, TimeLength delay) {
        schedulerRegistry.addFixedDelayTask(job(jobClass), delay.toMilliseconds());
    }

    public void triggerAtFixedRate(Class<? extends Job> jobClass, TimeLength interval) {
        schedulerRegistry.addFixedRateTask(job(jobClass), interval.toMilliseconds());
    }

    public void triggerByCronExpression(Class<? extends Job> jobClass, String cronExpression) {
        schedulerRegistry.addCronTask(job(jobClass), cronExpression);
    }

    public void triggerOnce(Job job, TimeLength delay) {
        Date scheduledTime = DateUtils.add(new Date(), Calendar.SECOND, (int) delay.toSeconds());

        config.scheduler.triggerOnceAt(job, scheduledTime);
    }

    private Runnable job(Class<? extends Job> jobClass) {
        AssertUtils.assertNotNull(jobClass, "jobClass cannot be null");
        JobExecutor jobExecutor = config.jobExecutor();
        jobExecutor.setJobClass(jobClass);
        return jobExecutor;
    }
}
