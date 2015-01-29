package com.framework.core.platform.scheduler;

import com.framework.core.platform.SpringObjectFactory;
import com.framework.core.utils.DateUtils;
import com.framework.core.utils.TimeLength;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author neo
 */
public class SchedulerImpl implements Scheduler {
    private static final int CORE_POOL_SIZE = 10;

    private SpringObjectFactory springObjectFactory;
    private final ScheduledThreadPoolExecutor executor;
    private final ConcurrentTaskScheduler scheduler;

    public SchedulerImpl() {
        executor = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);
        scheduler = new ConcurrentTaskScheduler(executor);
    }

    public ConcurrentTaskScheduler getScheduler() {
        return scheduler;
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }

    @Override
    public void triggerOnce(Job job) {
        triggerOnceAt(job, new Date());
    }

    @Override
    public void triggerOnceWithDelay(Job job, TimeLength delay) {
        Date time = DateUtils.add(new Date(), Calendar.SECOND, (int) delay.toSeconds());
        triggerOnceAt(job, time);
    }

    @Override
    public void triggerOnceAt(Job job, Date time) {
        JobExecutor jobExecutor = springObjectFactory.createBean(JobExecutor.class);
        jobExecutor.setJob(job);
        scheduler.schedule(jobExecutor, time);
    }

    @Inject
    public void setSpringObjectFactory(SpringObjectFactory springObjectFactory) {
        this.springObjectFactory = springObjectFactory;
    }
}
