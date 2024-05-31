package com.chris.allinone.javacore.skills.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author chrischen
 */
public class AClassInvocationHandler implements InvocationHandler {

    private AClass aClass;

    public AClassInvocationHandler(AClass aClass) {
        this.aClass = aClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());
        Object invokeResult = method.invoke(aClass, args);
        if (invokeResult instanceof String) {
            return invokeResult + " from proxy: " + aClass;
        }
        return invokeResult;
    }
}
