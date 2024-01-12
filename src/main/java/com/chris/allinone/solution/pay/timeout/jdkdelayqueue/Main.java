package com.chris.allinone.solution.pay.timeout.jdkdelayqueue;

import com.chris.allinone.solution.pay.timeout.jdkdelayqueue.pojo.Order;
import com.google.common.collect.Lists;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

public class Main {

    public static DelayQueue<OrderDelay> delayQueue = new DelayQueue<>();

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        Order order1 = Order.builder().id(111L).createTime(Date.from(now.minusSeconds(59).atZone(ZoneId.systemDefault()).toInstant())).build();
        Order order2 = Order.builder().id(222L).createTime(Date.from(now.minusSeconds(45).atZone(ZoneId.systemDefault()).toInstant())).build();
        Order order3 = Order.builder().id(333L).createTime(Date.from(now.minusSeconds(30).atZone(ZoneId.systemDefault()).toInstant())).build();
        Order order4 = Order.builder().id(444L).createTime(Date.from(now.minusSeconds(20).atZone(ZoneId.systemDefault()).toInstant())).build();
        Order order5 = Order.builder().id(555L).createTime(Date.from(now.minusSeconds(10).atZone(ZoneId.systemDefault()).toInstant())).build();
        Order order6 = Order.builder().id(666L).createTime(Date.from(now.minusSeconds(5).atZone(ZoneId.systemDefault()).toInstant())).build();
        Order order7 = Order.builder().id(777L).createTime(Date.from(now.minusSeconds(1).atZone(ZoneId.systemDefault()).toInstant())).build();

        List<Order> orderList = Lists.newArrayList(order1, order2, order3, order4, order5, order6, order7);
        for (Order order : orderList) {
            delayQueue.put(new OrderDelay(order, TimeUnit.SECONDS.toMillis(30)));
        }
        System.out.println("all orders to order delayed and add to DelayQueue");
        try {
            for (;;) {
                OrderDelay orderDelay = delayQueue.take();
                System.out.println("Go to DB to delete Order [" + orderDelay.getOrder().getId() + "]");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
