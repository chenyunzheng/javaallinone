package com.andrew.middleware.nacos.notify.listener;


import com.andrew.middleware.nacos.notify.Event;

import java.util.List;

/**
 * Subscribers to multiple events can be listened to.
 *
 * @author chrischen
 */
public abstract class SmartSubscriber extends Subscriber {
    
    /**
     * Returns which event type are smart subscriber interested in.
     *
     * @return The interested event types.
     */
    public abstract List<Class<? extends Event>> subscribeTypes();
    
    @Override
    public final Class<? extends Event> subscribeType() {
        return null;
    }
    
    @Override
    public final boolean ignoreExpireEvent() {
        return false;
    }
}
