package com.chris.allinone.spring.skills;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author chrischen
 */
@Component
public class BeanNamePostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().getName().contains("com.chris.allinone")) {
            System.out.printf(">>> bean=%s, beanName=%s%n", bean, beanName);
        }
        return bean;
    }
}
