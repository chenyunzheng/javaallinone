package com.chris.allinone.solution.pay.timeout.timewheel;

import com.chris.allinone.solution.pay.timeout.jdkdelayqueue.pojo.Order;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        LocalDateTime now = LocalDateTime.now();
        OrderTimeoutTimerTask timeoutTimerTask = new OrderTimeoutTimerTask(
                Order.builder().id(111L).createTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant())).build()
        );
        Timer timer = new HashedWheelTimer();
        timer.newTimeout(timeoutTimerTask, 30, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(60);
    }
}
