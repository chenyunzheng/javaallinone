package com.chris.allinone.javacore.threadpool;


import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chrischen
 */
public class ScheduledPoolDemo {

    static final AtomicInteger COUNTER = new AtomicInteger(0);

    public static void main(String[] args) {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
                new ScheduledThreadPoolExecutor(10, new DefaultRejectedExecutionHandler());
        scheduledThreadPoolExecutor.scheduleAtFixedRate(() -> {
            try {
                if (COUNTER.incrementAndGet() >= 5) {
                    throw new IllegalStateException("exception");
                }
                Thread thread = Thread.currentThread();
                System.out.println(COUNTER.get() + ". " + thread.getName() + " execute task.");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (Throwable throwable) {
                System.out.println("出现异常，但未终止定时任务...");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }
}
