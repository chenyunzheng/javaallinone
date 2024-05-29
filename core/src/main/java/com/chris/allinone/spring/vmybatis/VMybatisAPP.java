package com.chris.allinone.spring.vmybatis;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * @author chrischen
 */
@Configuration
public class VMybatisAPP {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(VMybatisAPP.class);
        applicationContext.refresh();

    }
}
