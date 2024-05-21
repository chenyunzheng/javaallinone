package com.chris.allinone.solution.pay.timeout.jdkdelayqueue;

import com.chris.allinone.solution.pay.timeout.jdkdelayqueue.pojo.Order;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * sb了，两个jvm实例，咋可能数据互通
 * 进程间数据互通：pipeline，信号，数据库，MQ，共享内存，Socket
 */
public class Producer {

    public static void main(String[] args) {
        Main.delayQueue.put(new OrderDelay(
                Order.builder().id(999999L).createTime(Date.from(LocalDateTime.now().minusSeconds(15).atZone(ZoneId.systemDefault()).toInstant())).build(),
                TimeUnit.SECONDS.toMillis(30)
        ));
        System.out.println(">>> Add success!");
    }
}
