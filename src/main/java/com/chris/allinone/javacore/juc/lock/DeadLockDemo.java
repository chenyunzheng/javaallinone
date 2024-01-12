package com.chris.allinone.javacore.juc.lock;

/**
 * @author chrischen
 */
public class DeadLockDemo {

    static String lockA = "A";
    static String lockB = "B";

    public static void main(String[] args) {
        DeadLockDemo lockDemo = new DeadLockDemo();
        new Thread(() -> {
            lockDemo.methodA();
        }, "T1").start();
        new Thread(() -> {
            lockDemo.methodB();
        }, "T2").start();
    }

    public void methodA() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "获取A锁，运行。。");
            try {
                // 调用wait(long timeoutMillis)，释放锁同时让线程进入条件等待队列，即_WaitSet，打破死锁循环
                lockA.wait(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "获取B锁，运行。。");
            }
        }
    }

    public void methodB() {
        synchronized (lockB) {
            System.out.println(Thread.currentThread().getName() + "获取B锁，运行。。");
            try {
                lockB.wait(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockA) {
                System.out.println(Thread.currentThread().getName() + "获取A锁，运行。。");
            }
        }
    }
}
