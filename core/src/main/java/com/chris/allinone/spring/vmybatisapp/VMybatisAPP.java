package com.chris.allinone.spring.vmybatisapp;

import com.andrew.vmybatis.spring.VMapperScan;
import com.chris.allinone.spring.vmybatisapp.dao.AccountMapper;
import com.chris.allinone.spring.vmybatisapp.dao.OrderMapper;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author chrischen
 */
@Configuration
@ComponentScan
@VMapperScan("com.chris.allinone.spring.vmybatisapp.dao")
public class VMybatisAPP {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(VMybatisAPP.class);
        applicationContext.refresh();

        OrderMapper orderMapper = applicationContext.getBean("orderMapper", OrderMapper.class);
        System.out.println(orderMapper.selectById());
        AccountMapper accountMapper = applicationContext.getBean("accountMapper", AccountMapper.class);
        System.out.println(accountMapper.selectByUserId());

    }
}
