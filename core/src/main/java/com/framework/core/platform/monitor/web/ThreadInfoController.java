package com.framework.core.platform.monitor.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.core.platform.monitor.Pass;
import com.framework.core.platform.web.rest.FrameWorkRestController;

import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MonitorInfo;
import java.lang.management.ThreadInfo;

/**
 * @author neo
 */
@Controller
@Pass
public class ThreadInfoController extends FrameWorkRestController {
    @RequestMapping(value = "/monitor/threads", produces = "text/plain", method = RequestMethod.GET)
    @ResponseBody
    public String threadInfo() {
        StringBuilder builder = new StringBuilder();
        ThreadInfo[] threads = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
        for (ThreadInfo thread : threads) {
            builder.append(toString(thread)).append('\n');
        }
        return builder.toString();
    }

    // from ThreadInfo.toString with printing all stack frames
    private String toString(ThreadInfo threadInfo) {
        StringBuilder builder = new StringBuilder()
                .append('\"').append(threadInfo.getThreadName()).append('\"').append(" Id=").append(threadInfo.getThreadId()).append(' ').append(threadInfo.getThreadState());
        if (threadInfo.getLockName() != null) {
            builder.append(" on ").append(threadInfo.getLockName());
        }
        if (threadInfo.getLockOwnerName() != null) {
            builder.append(" owned by \"").append(threadInfo.getLockOwnerName()).append("\" Id=").append(threadInfo.getLockOwnerId());
        }
        if (threadInfo.isSuspended()) {
            builder.append(" (suspended)");
        }
        if (threadInfo.isInNative()) {
            builder.append(" (in native)");
        }
        builder.append('\n');
        printStackTrace(builder, threadInfo);

        LockInfo[] locks = threadInfo.getLockedSynchronizers();
        if (locks.length > 0) {
            builder.append("\n\tNumber of locked synchronizers = ").append(locks.length);
            builder.append('\n');
            for (LockInfo lock : locks) {
                builder.append("\t- ").append(lock);
                builder.append('\n');
            }
        }
        builder.append('\n');
        return builder.toString();
    }

    private void printStackTrace(StringBuilder builder, ThreadInfo threadInfo) {
        StackTraceElement[] stackTrace = threadInfo.getStackTrace();
        for (int i = 0, stackTraceLength = stackTrace.length; i < stackTraceLength; i++) {
            StackTraceElement stack = stackTrace[i];
            builder.append("\tat ").append(stack);
            builder.append('\n');
            if (i == 0 && threadInfo.getLockInfo() != null) {
                Thread.State threadState = threadInfo.getThreadState();
                switch (threadState) {
                    case BLOCKED:
                        builder.append("\t-  blocked on ").append(threadInfo.getLockInfo());
                        builder.append('\n');
                        break;
                    case WAITING:
                        builder.append("\t-  waiting on ").append(threadInfo.getLockInfo());
                        builder.append('\n');
                        break;
                    case TIMED_WAITING:
                        builder.append("\t-  waiting on ").append(threadInfo.getLockInfo());
                        builder.append('\n');
                        break;
                    default:
                        break;
                }
            }

            for (MonitorInfo monitorInfo : threadInfo.getLockedMonitors()) {
                if (monitorInfo.getLockedStackDepth() == i) {
                    builder.append("\t-  locked ").append(monitorInfo);
                    builder.append('\n');
                }
            }
        }
    }
}
