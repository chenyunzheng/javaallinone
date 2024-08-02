package com.chris.allinone.javacore.threadpool.monitor;

/**
 * @author chrischen
 */
public class ThreadPoolMonitorFactory {

    public static ThreadPoolMonitor getThreadPoolMonitor() {
        throw new UnsupportedOperationException();
    }

    public static ThreadPoolMonitor getSimpleThreadPoolMonitor() {
        return new SimpleThreadPoolMonitor();
    }
}
