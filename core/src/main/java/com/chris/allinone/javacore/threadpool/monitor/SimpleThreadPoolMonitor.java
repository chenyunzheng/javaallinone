package com.chris.allinone.javacore.threadpool.monitor;

import com.chris.allinone.javacore.threadpool.queuelast.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 */
@Slf4j
public class SimpleThreadPoolMonitor implements ThreadPoolMonitor {

    private ThreadPoolExecutor target;
    private ScheduledThreadPoolExecutor schedule;

    public SimpleThreadPoolMonitor() {
        this.schedule = new ScheduledThreadPoolExecutor(1, new NamedThreadFactory("monitor"));
    }

    @Override
    public void setTarget(ThreadPoolExecutor threadPool) {
        this.target = threadPool;
    }

    @Override
    public void start() {
        this.schedule.scheduleAtFixedRate(() -> {
            try {
                log.info("================================");
                log.info("Core pool size: {}", target.getCorePoolSize());
                log.info("Maximum pool size: {}", target.getMaximumPoolSize());
                log.info("Pool size: {}", target.getPoolSize());
                log.info("Active threads: {}", target.getActiveCount());
                log.info("Number of tasks scheduled: {}", target.getTaskCount());
                log.info("Number of tasks completed: {}", target.getCompletedTaskCount());
                log.info("Number of tasks in Queue: {}", target.getQueue().size());
                log.info("================================");
            } catch (Exception e) {
                log.error("exception", e);
            }
        }, 500, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public void end() {
        this.schedule.shutdown();
    }
}
