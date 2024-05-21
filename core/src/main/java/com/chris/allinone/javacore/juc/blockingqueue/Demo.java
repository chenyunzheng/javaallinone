package com.chris.allinone.javacore.juc.blockingqueue;

import org.springframework.util.Assert;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 */
public class Demo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        /**
         * add,remove,element
         */
        blockingQueue.add("a");
        blockingQueue.add("b");
        blockingQueue.add("c");
        //Exception in thread "main" java.lang.IllegalStateException: Queue full
        //blockingQueue.add("d");
        blockingQueue.remove();
        blockingQueue.remove();
        blockingQueue.remove();
        //Exception in thread "main" java.util.NoSuchElementException
        //blockingQueue.remove();

        //Exception in thread "main" java.util.NoSuchElementException
        //blockingQueue.element();

        /**
         * offer,poll,peek
         */
        blockingQueue.offer("a");
        blockingQueue.offer("b");
        blockingQueue.offer("c");
        boolean success = blockingQueue.offer("d");
        System.out.println(success);
        //System.out.println(blockingQueue.peek());
        blockingQueue.poll();
        blockingQueue.poll();
        blockingQueue.poll();
        String poll = blockingQueue.poll();
        System.out.println(poll);

        /**
         * put,take 阻塞式
         */
        try {
            new Thread(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                    String take = blockingQueue.take();
                    System.out.println(take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
            blockingQueue.put("a");
            blockingQueue.put("b");
            blockingQueue.put("c");
            System.out.println("准备入队 d ...");
            blockingQueue.put("d");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * offer,poll timeout
         */
        try {
            boolean status = blockingQueue.offer("a", 2, TimeUnit.SECONDS);
            Assert.isTrue(!status, "blockingQueue.offer() with timeout is wrong");
            blockingQueue.poll();
            blockingQueue.poll();
            blockingQueue.poll();
            String poll1 = blockingQueue.poll(2, TimeUnit.SECONDS);
            Assert.isNull(poll1, "blockingQueue.poll() with timeout is wrong");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
