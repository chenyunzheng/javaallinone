package com.chris.allinone.javacore.juc.ext.jucdemo.sync;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 * JDK11默认BiasedLockingStartupDelay = 0
 * JDK8默认BiasedLockingStartupDelay = 4000
 */
public class ObjectLayout {

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        Test test = new Test();
        System.out.println("headerSize = " + ClassLayout.parseInstance(test).headerSize());
        System.out.println("instanceSize = " + ClassLayout.parseInstance(test).instanceSize());
        System.out.println(ClassLayout.parseInstance(test).toPrintable());
        System.out.println(ClassLayout.parseInstance(ObjectLayout.class).toPrintable());
    }

    static class Test {
        private long s;
    }
}
