package com.chris.allinone.solution.pay.timeout.timewheel;

import com.chris.allinone.solution.pay.timeout.jdkdelayqueue.pojo.Order;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author chrischen
 * TimerTask: 计时任务
 */
public class OrderTimeoutTimerTask implements TimerTask {

    private Order order;

    public OrderTimeoutTimerTask(Order order) {
        this.order = order;
    }

    @Override
    public void run(Timeout timeout) throws Exception {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + " Go to DB to delete Order [" + this.order.getId() + "]");
    }
}
