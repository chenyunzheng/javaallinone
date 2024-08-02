package com.chris.allinone.javacore.threadpool.queuelast;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chrischen
 */
public class NamedThreadFactory implements ThreadFactory {

    private final AtomicInteger threadId;
    private final String threadNamePrefix;

    public NamedThreadFactory(String threadNamePrefix) {
        this.threadId = new AtomicInteger(0);
        this.threadNamePrefix = Objects.isNull(threadNamePrefix) ? "default" : threadNamePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, String.format("%s-thread-%d", this.threadNamePrefix, this.threadId.getAndIncrement()));
    }
}
