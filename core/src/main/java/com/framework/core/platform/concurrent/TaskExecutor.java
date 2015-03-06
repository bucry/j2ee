package com.framework.core.platform.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.inject.Inject;

import com.framework.core.platform.SpringObjectFactory;

/**
 * @author neo
 */
public class TaskExecutor {
    private final ExecutorService executorService;
    private SpringObjectFactory springObjectFactory;

    public TaskExecutor(int threadPoolSize) {
        executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public long getTaskCount() {
        return ((ThreadPoolExecutor) executorService).getTaskCount();
    }

    public <T> List<T> executeAll(List<? extends Callable<T>> tasks) {
        try {
            List<Callable<T>> proxyTasks = createProxyTasks(tasks);
            List<Future<T>> futures = executorService.invokeAll(proxyTasks);
            List<T> results = new ArrayList<T>(futures.size());
            for (Future<T> future : futures) {
                results.add(future.get());
            }
            return results;
        } catch (InterruptedException e) {
            throw new TaskExecutionException(e);
        } catch (ExecutionException e) {
            throw new TaskExecutionException(e);
        }
    }

    private <T> List<Callable<T>> createProxyTasks(List<? extends Callable<T>> tasks) {
        List<Callable<T>> proxyTasks = new ArrayList<Callable<T>>(tasks.size());
        for (Callable<T> task : tasks) {
            Callable<T> taskBean = springObjectFactory.initializeBean(task);
            proxyTasks.add(new TaskProxy<T>(taskBean));
        }
        return proxyTasks;
    }

    @Inject
    public void setSpringObjectFactory(SpringObjectFactory springObjectFactory) {
        this.springObjectFactory = springObjectFactory;
    }
}
