package com.chris.allinone.javacore.threadpool.monitor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author chrischen
 */
public interface ThreadPoolMonitor {

    void setTarget(ThreadPoolExecutor threadPool);

    void start();

    void end();

}
