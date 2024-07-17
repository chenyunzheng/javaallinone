package com.andrew.middleware.nacos.task;

/**
 * Nacos task.
 *
 * @author chrischen
 */
public interface NacosTask {
    
    /**
     * Judge Whether this nacos task should do.
     *
     * @return true means the nacos task should be done, otherwise false
     */
    boolean shouldProcess();
}
