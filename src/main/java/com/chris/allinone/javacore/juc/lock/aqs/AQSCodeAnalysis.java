package com.chris.allinone.javacore.juc.lock.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQSCodeAnalysis {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + "获取锁，运行中。。。");
                } finally {
                    lock.unlock();
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(2);
    }
}
