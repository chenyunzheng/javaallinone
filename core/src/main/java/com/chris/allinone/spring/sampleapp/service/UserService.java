package com.chris.allinone.spring.sampleapp.service;


import com.chris.allinone.spring.sampleapp.annotation.Value;
import com.chris.allinone.spring.source.Autowired;
import com.chris.allinone.spring.source.Component;
import com.chris.allinone.spring.source.InitializingBean;

/**
 * @author chrischen
 */
@Component
//@Scope("prototype")
public class UserService implements InitializingBean {

    @Autowired
    private OrderService orderService;

    @Value("chris")
    private String defaultUser;

    public void say() {
        System.out.println(">>> hello world!");
        System.out.println(">>> " + orderService);
        System.out.println(">>> " + defaultUser);
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println(">>> all properties valid");
    }
}
