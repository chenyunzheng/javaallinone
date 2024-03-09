package com.chris.allinone.spring.sampleapp.annotation;

import com.chris.allinone.spring.sampleapp.service.OrderServiceImpl;
import com.chris.allinone.spring.source.BeanPostProcessor;
import com.chris.allinone.spring.source.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author chrischen
 */
@Component
public class ProxyClassBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (bean.getClass().isAnnotationPresent(ProxyClass.class)) {
            return Proxy.newProxyInstance(bean.getClass().getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("before native call");
                    Object result = method.invoke(bean, args);
                    System.out.println(result);
                    System.out.println("after native call");
                    return result;
                }
            });
        }
        return bean;
    }
}
