package com.andrew.middleware.nacos.task;

/**
 * Abstract task which should be executed immediately.
 *
 * @author chrischen
 */
public abstract class AbstractExecuteTask implements NacosTask, Runnable {
    
    protected static final long INTERVAL = 3000L;
    
    @Override
    public boolean shouldProcess() {
        return true;
    }
}
