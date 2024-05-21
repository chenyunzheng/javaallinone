package com.chris.allinone.solution.pay.timeout.db;

import com.chris.allinone.solution.pay.timeout.db.entity.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 */
@EnableScheduling
@Component
public class ScheduleDBScan {

    @Resource
    private OrderDAO orderDAO;

    @Scheduled(initialDelay = 5, fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    @Transactional(rollbackFor = Exception.class)
    public void scanTimeoutOrderList() {
        LocalDateTime nowDateTime = LocalDateTime.now();
        String now = nowDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(now + ">>> db scan");
        String nowMinus30s = nowDateTime.minusSeconds(30L).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        List<Order> timeoutOrderList = orderDAO.findTimeoutOrderList(nowMinus30s);
        for (Order order : timeoutOrderList) {
            System.out.println(order.getOrderId());
            Order updatedOrder = Order.builder().id(order.getId()).orderStatus((short) 2).build();
            orderDAO.saveOrderStatusById(updatedOrder);
        }
        //orderDAO.saveAll(timeoutOrderList);
    }

}
