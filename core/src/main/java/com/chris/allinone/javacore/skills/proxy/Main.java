package com.chris.allinone.javacore.skills.proxy;

import java.lang.reflect.Proxy;

/**
 * @author chrischen
 */
public class Main {

    public static void main(String[] args) {
        AClass aClass = new AClass();
        System.out.println(aClass.say("andrew"));
        //with proxy
        Object proxyInstance = Proxy.newProxyInstance(AClass.class.getClassLoader(),
                new Class<?>[]{AInterface.class}, new AClassInvocationHandler(aClass));
        AInterface aInterface = (AInterface)proxyInstance;
        System.out.println(aInterface.say("andrew"));
    }
}
