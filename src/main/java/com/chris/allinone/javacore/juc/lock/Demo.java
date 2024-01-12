package com.chris.allinone.javacore.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 */
public class Demo {

    static int sum = 0;

    public static void main(String[] args) throws InterruptedException {
        AndrewLock lock = new AndrewLock();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                lock.lock();
                for (int j = 0; j < 1000; j++) {
                    sum++;
                }
                lock.unlock();
            }).start();
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("sum = " + sum);
    }
}
