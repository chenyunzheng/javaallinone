package com.chris.allinone.javacore.threadpool.queuelast;

import com.chris.allinone.javacore.threadpool.monitor.ThreadPoolMonitor;
import com.chris.allinone.javacore.threadpool.monitor.ThreadPoolMonitorFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 * core thread -> max thread -> queue
 */
@Slf4j
public class QueueLastThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 10, 10, TimeUnit.SECONDS,
                new QueueLastLinkedBlockingQueue<>(20), new NamedThreadFactory("demo"), new QueueLastRejectedExecutionHandler());
        ThreadPoolMonitor simpleThreadPoolMonitor = ThreadPoolMonitorFactory.getSimpleThreadPoolMonitor();
        simpleThreadPoolMonitor.setTarget(threadPoolExecutor);
        simpleThreadPoolMonitor.start();
        for (int i = 0; i < 20; i++) {
            TimeUnit.SECONDS.sleep(1);
            final int iter = i;
            threadPoolExecutor.submit(() -> {
                try {
                    //business logic
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                log.info(">>> task " + iter + " completed.");
            });
        }
        simpleThreadPoolMonitor.end();
    }
}
