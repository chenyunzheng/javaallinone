package com.chris.allinone;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author chrischen
 */
public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(AppConfig.class);
        applicationContext.refresh();
    }
}
