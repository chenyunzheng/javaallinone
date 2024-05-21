package com.chris.allinone.designpattern.singleton.enums;

/**
 * @author chrischen
 * 枚举实现单例 => 由jvm保证
 */

public enum Singleton {

    INSTANCE;

    public void sayHello() {
        System.out.println("hello");
    }
}
