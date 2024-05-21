package com.chris.allinone.designpattern.singleton;

/**
 * @author chrischen
 */
public class Main {

    public static void main(String[] args) {
        Singleton singleton = Singleton.getInstanceByInnerStaticClass();
        singleton.sayHello();
    }
}
