package com.andrew.middleware.nacos.task;

/**
 * Abstract task which can delay and merge.
 *
 * @author chrischen
 */
public abstract class AbstractDelayTask implements NacosTask {
    
    /**
     * Task time interval between twice processing, unit is millisecond.
     */
    private long taskInterval;
    
    /**
     * The time which was processed at last time, unit is millisecond.
     */
    private long lastProcessTime;
    
    /**
     * The default time interval, in milliseconds, between tasks.
     */
    protected static final long INTERVAL = 1000L;
    
    /**
     * merge task.
     *
     * @param task task
     */
    public abstract void merge(AbstractDelayTask task);
    
    public void setTaskInterval(long interval) {
        this.taskInterval = interval;
    }
    
    public long getTaskInterval() {
        return this.taskInterval;
    }
    
    public void setLastProcessTime(long lastProcessTime) {
        this.lastProcessTime = lastProcessTime;
    }
    
    public long getLastProcessTime() {
        return this.lastProcessTime;
    }
    
    @Override
    public boolean shouldProcess() {
        return (System.currentTimeMillis() - this.lastProcessTime >= this.taskInterval);
    }
    
}
