package com.chris.allinone.javacore.juc;

import com.chris.allinone.javacore.UnsafeFactory;

import java.util.concurrent.locks.LockSupport;

/**
 * @author chrischen
 * -server -XX:+UnlockDiagnosticVMOptions -XX:+PrintAssembly -XX:+LogCompilation -XX:LogFile=jit.log
 */
public class VisibilityTest {

    private Integer count = 0;
    private boolean flag = true;

    public Thread show() {
        Thread thread = new Thread(() -> {
            System.out.println("count++ start");
            while (flag) {
                count++;
                //1. 出让cpu时间片并切换线程上下文,使得从主内存中加载flag
                //Thread.yield();

                //2.显式调用内存屏障,使缓存失效
                //UnsafeFactory.getUnsafe().storeFence();

                //3.synchronized也是应用了内存屏障,使缓存失效
                //System.out.println();

                //4.同样也是应用了内存屏障,使缓存失效
                //LockSupport.unpark(Thread.currentThread());

                //5.同样也是应用了内存屏障,使缓存失效
//                try {
//                    Thread.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                //6.这种长时间等待的方式并不是使用内存屏障,而是从硬件层面使得缓存失效再从主内存中重新加载
                //waitNanos(10000);
                //waitNanos(1000000);
            }
            String name = Thread.currentThread().getName();
            System.out.println(name + " stopped at count = " + count);
        }, "ThreadA");
        thread.start();
        return thread;
    }

    public Thread stop() {
        Thread thread = new Thread(() -> {
            flag = false;
            String name = Thread.currentThread().getName();
            System.out.println(name + " set flag = false");
        }, "ThreadB");
        thread.start();
        return thread;
    }

    public static void main(String[] args) throws InterruptedException {
        VisibilityTest test = new VisibilityTest();
        Thread threadA = test.show();
//        waitNanos(10000000);
        Thread.sleep(2000);
        Thread threadB = test.stop();
        threadA.join();
        threadB.join();
    }

    public static void waitNanos(long interval) {
        long start = System.nanoTime();
        long end = start + interval;
        long now;
        do {
            now = System.nanoTime();
        } while (end > now);
    }
}
