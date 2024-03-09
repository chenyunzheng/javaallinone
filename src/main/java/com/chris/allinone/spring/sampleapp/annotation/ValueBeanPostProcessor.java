package com.chris.allinone.spring.sampleapp.annotation;

import com.chris.allinone.spring.source.BeanPostProcessor;
import com.chris.allinone.spring.source.Component;
import lombok.SneakyThrows;

import java.lang.reflect.Field;

/**
 * @author chrischen
 */
@Component
public class ValueBeanPostProcessor implements BeanPostProcessor {

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        for (Field field : bean.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Value.class)) {
                Value annotation = field.getAnnotation(Value.class);
                String value = annotation.value();
                field.setAccessible(true);
                field.set(bean, value);
            }
        }
        return bean;
    }
}
