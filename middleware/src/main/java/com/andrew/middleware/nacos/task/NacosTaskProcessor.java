package com.andrew.middleware.nacos.task;

/**
 * Task processor.
 *
 * @author chrischen
 */
public interface NacosTaskProcessor {
    
    /**
     * Process task.
     *
     * @param task     task.
     * @return process task result.
     */
    boolean process(NacosTask task);
}
