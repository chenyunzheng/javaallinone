package com.chris.allinone.javacore.juc.designpattern.avoidshare.thread_specific_storage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chrischen
 * -Xmx128m -Xms128m
 * todo: jdk11，结合jvisualvm，发现GC能明显回收eden区，包括如下nThread个Temp对象，未看出内存泄露问题
 * todo: 可能同GC相关
 * done: 线程池 + ThreadLocal可能出现内存泄漏。
 * 线程池中线程可以长时间存在，对应的thread对象在堆中长期存在。当线程执行完某个任务后，栈回收且对应堆中对象若无其他引用则等待GC。
 * 线程对应的ThreadLocalMap，其Entry的key为弱引用，GC时会回收key指向的ThreadLocal对象，但对应的value值因Thread对象存在则不会被回收。
 * 最佳实践：ThreadLocal在使用后，务必remove，尤其是在线程池中，保证线程安全。
 */
public class ThreadLocalMemoryLeak {

    private static final ThreadLocal<Temp> LOCAL = ThreadLocal.withInitial(()-> new Temp());

    public static void main(String[] args) throws InterruptedException {
        TimeUnit.SECONDS.sleep(5);
        int nThread = 10;
        AtomicInteger counter = new AtomicInteger(0);
//        for (int i = 0; i < nThread; i++) {
//            new Thread(() -> {
//                Temp temp = LOCAL.get();
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                //LOCAL.remove();
//            }).start();
//        }
//        TimeUnit.SECONDS.sleep(5 * 60);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10), r -> new Thread(r, "AA-" + counter.getAndIncrement()));

        for (int i = 0; i < nThread; i++) {
            TimeUnit.SECONDS.sleep(3);
            threadPoolExecutor.execute(() -> {
//                Temp temp = new Temp();
                Temp temp = LOCAL.get();
            });
        }
    }

    static class Temp {
        byte[] value = new byte[5 * 1000 * 1000]; //5M
    }
}
