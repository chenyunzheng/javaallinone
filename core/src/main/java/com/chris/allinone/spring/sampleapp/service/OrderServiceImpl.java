package com.chris.allinone.spring.sampleapp.service;

import com.chris.allinone.spring.sampleapp.annotation.ProxyClass;
import com.chris.allinone.spring.source.Component;

/**
 * @author chrischen
 */
@Component("orderService")
@ProxyClass
public class OrderServiceImpl implements OrderService {

    @Override
    public void say() {
        System.out.println("say hi");
    }
}
