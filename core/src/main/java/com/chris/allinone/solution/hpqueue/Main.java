package com.chris.allinone.solution.hpqueue;

import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;

import java.util.concurrent.CountDownLatch;

/**
 * @author chrischen
 * @date 2025/7/9
 * @desc Main
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        MpscArrayQueue<String> arrayQueue = new MpscArrayQueue<>(30);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        int nThread = 10;
        for (int i = 0; i < nThread; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String value = "v" + finalI;
                if (arrayQueue.offer(value)) {
                    System.out.println(Thread.currentThread().getName() + " offer value: " + value);
                }
            }, "t-" + i).start();
        }
        Thread.sleep(200);
        countDownLatch.countDown();
        for (int i = 0; i < 100; i++) {
            String poll = arrayQueue.poll();
            System.out.println(Thread.currentThread().getName() + " poll value: " + poll);
        }
    }
}
