package com.chris.allinone.solution.pay.timeout.jdkdelayqueue;

import com.chris.allinone.solution.pay.timeout.jdkdelayqueue.pojo.Order;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 */
public class OrderDelay implements Delayed {

    private Order order;
    /**
     * the end of timeout in milliseconds
    */
    private long timeout;

    public OrderDelay(Order order, long timeout) {
        this.order = order;
        this.timeout = order.getCreateTime().getTime() + timeout;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.timeout - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this == o) {
            return 0;
        }
        OrderDelay other = (OrderDelay) o;
        return this.order.getCreateTime().compareTo(other.order.getCreateTime());
    }

}
