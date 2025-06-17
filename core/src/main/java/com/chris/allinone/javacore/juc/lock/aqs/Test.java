package com.chris.allinone.javacore.juc.lock.aqs;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 * @date 2025/6/17
 * @desc Test
 */
@Slf4j
public class Test {

    public static void main(String[] args) throws InterruptedException {
        //如何保证3个线程同时并发？
        threadConcurrency();

        //如何使3个线程依次执行？
        threadRunInSeq();
    }

    private static void threadConcurrency() throws InterruptedException {
        int size = 3;
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < size; i++) {
            TimeUnit.SECONDS.sleep(2);
            new Thread(() -> {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info(Thread.currentThread().getName() + ": " + System.currentTimeMillis());
            }, "t-" + i).start();
        }
        TimeUnit.SECONDS.sleep(10);
        countDownLatch.countDown();
    }

    private static void threadRunInSeq() throws InterruptedException {
        Semaphore semaphoreA = new Semaphore(1);
        Semaphore semaphoreB = new Semaphore(1);
        Semaphore semaphoreC = new Semaphore(1);
        semaphoreB.acquire(1);
        semaphoreC.acquire(1);
        new Thread(() -> {
            while (true) {
                try {
                    semaphoreA.acquire(1);
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                    semaphoreB.release(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "A").start();

        new Thread(() -> {
            while (true) {
                try {
                    semaphoreB.acquire(1);
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                    semaphoreC.release(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "B").start();

        new Thread(() -> {
            while (true) {
                try {
                    semaphoreC.acquire(1);
                    System.out.println(Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(1);
                    semaphoreA.release(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "C").start();

    }
}
