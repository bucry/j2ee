package com.framework.core.platform;

import com.framework.core.platform.scheduler.JobExecutor;
import com.framework.core.platform.scheduler.SchedulerImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import javax.inject.Inject;

/**
 * @author neo
 */
@EnableScheduling
public abstract class DefaultSchedulerConfig implements SchedulingConfigurer {
    @Inject
    protected Environment env;

    @Inject
    SchedulerImpl scheduler;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    protected JobExecutor jobExecutor() {
        return new JobExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar registry) {
        registry.setTaskScheduler(scheduler.getScheduler());
        configure(new JobRegistry(registry, this));
    }

    protected abstract void configure(JobRegistry registry);
}
