package com.chris.allinone.designpattern.singleton.enums;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        int count = 30;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    Singleton singleton = Singleton.INSTANCE;
                    System.out.println(singleton.hashCode());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        countDownLatch.countDown();
        TimeUnit.SECONDS.sleep(2);
    }
}
