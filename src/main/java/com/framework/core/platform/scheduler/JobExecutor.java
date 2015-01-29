package com.framework.core.platform.scheduler;

import com.framework.core.log.TraceLogger;
import com.framework.core.platform.SpringObjectFactory;
import com.framework.core.platform.monitor.ExceptionStatistic;
import com.framework.core.utils.AssertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * @author neo
 */
public class JobExecutor implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(JobExecutor.class);
    private ExceptionStatistic exceptionStatistic;
    private SpringObjectFactory springObjectFactory;

    private Job job;
    private Class<? extends Job> jobClass;

    @Override
    public void run() {
        TraceLogger.get().initialize();
        try {
            Job jobProxy = createJob();
            logger.debug("start execute job, jobClass={}", jobProxy.getClass().getName());
            jobProxy.execute();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            exceptionStatistic.failed(e);
        } finally {
            logger.debug("finish execute job");
            TraceLogger.get().clear();
        }
    }

    private Job createJob() {
        if (job != null) {
            return springObjectFactory.initializeBean(job);
        } else {
            return springObjectFactory.createBean(jobClass);
        }
    }

    public void setJob(Job job) {
        AssertUtils.assertNull(jobClass, "job can not be set with jobClass");
        this.job = job;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        AssertUtils.assertNull(job, "jobClass can not be set with job");
        this.jobClass = jobClass;
    }

    @Inject
    public void setExceptionStatistic(ExceptionStatistic exceptionStatistic) {
        this.exceptionStatistic = exceptionStatistic;
    }

    @Inject
    public void setSpringObjectFactory(SpringObjectFactory springObjectFactory) {
        this.springObjectFactory = springObjectFactory;
    }
}
