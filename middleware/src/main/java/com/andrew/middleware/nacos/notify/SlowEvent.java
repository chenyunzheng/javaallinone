package com.andrew.middleware.nacos.notify;

/**
 * This event share one event-queue.
 *
 * @author chrischen
 */
public abstract class SlowEvent extends Event {
    
    @Override
    public long sequence() {
        return 0;
    }
}