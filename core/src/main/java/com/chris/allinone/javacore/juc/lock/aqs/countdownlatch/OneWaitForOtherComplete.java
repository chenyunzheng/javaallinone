package com.chris.allinone.javacore.juc.lock.aqs.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * @author chrischen
 */
public class OneWaitForOtherComplete {
    public static void main(String[] args) throws InterruptedException {
        //等待任务数：2
        CountDownLatch countDownLatch = new CountDownLatch(2);
        new Thread(() -> {
            try {
                Thread.sleep(2000);
                System.out.println("t1完成任务1");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("t2完成任务2");
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
        //汇总任务等待任务1和2完成
        countDownLatch.await();
        System.out.println("汇总任务完成");
        System.out.println("总任务完成！！");
    }
}
