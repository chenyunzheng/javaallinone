package com.chris.allinone.javacore.juc.lock.aqs.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 * 单个线程让其他线程等待
 */
public class SimulateConcurrency {

    /**
     * 模拟并发
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    System.out.println(System.currentTimeMillis() + " ==> " + finalI + "执行中。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(1);
        countDownLatch.countDown();
    }
}
