package com.andrew.middleware.nacos.lifecycle;

import com.andrew.middleware.nacos.exception.NacosException;

/**
 * An interface is used to define the resource's close and shutdown, such as IO Connection and ThreadPool.
 *
 * @author chrischen
 */
public interface Closeable {
    
    /**
     * Shutdown the Resources, such as Thread Pool.
     *
     * @throws NacosException exception.
     */
    void shutdown() throws NacosException;
    
}
