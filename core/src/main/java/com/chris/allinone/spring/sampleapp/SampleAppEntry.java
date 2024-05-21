package com.chris.allinone.spring.sampleapp;

import com.chris.allinone.spring.sampleapp.service.OrderService;
import com.chris.allinone.spring.sampleapp.service.UserService;
import com.chris.allinone.spring.source.ApplicationContext;
import com.chris.allinone.spring.source.ComponentScan;

/**
 * @author chrischen
 */
@ComponentScan(value = "com.chris.allinone.spring.sampleapp")
public class SampleAppEntry {

    public static void main(String[] args) {
        ApplicationContext context = new ApplicationContext(SampleAppEntry.class, args);
        //UserService userService1 = (UserService)context.getBean("userService");
        //System.out.println(userService1);
        UserService userService = (UserService)context.getBean("userService");
        System.out.println(userService);
        userService.say();

        OrderService orderService = (OrderService)context.getBean("orderService");
        orderService.say();

    }
}
