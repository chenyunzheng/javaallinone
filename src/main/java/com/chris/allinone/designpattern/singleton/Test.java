package com.chris.allinone.designpattern.singleton;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) throws InterruptedException {
        int count = 20;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    Singleton instance = Singleton.getInstanceByInnerStaticClass();
                    System.out.println(instance);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }).start();
        }
        countDownLatch.countDown();
        TimeUnit.SECONDS.sleep(2);
    }
}
