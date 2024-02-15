package com.chris.allinone.javacore.juc.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author chrischen
 */
@Slf4j
public class FutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //juc
        Callable<String> task = () -> {
            log.info("sleep 5s");
            TimeUnit.SECONDS.sleep(5);
            return "hello future!";
        };
        FutureTask<String> futureTask = new FutureTask<>(task);
        new Thread(futureTask).start();
        String s = futureTask.get();
        log.info(Thread.currentThread().getName() + " - result from callable: " + s);
        //myfuturetask
        MyFutureTask<String> myFutureTask = new MyFutureTask<>(task);
        new Thread(myFutureTask).start();
        new Thread(() -> {
            try {
                String s1 = myFutureTask.get();
                log.info(Thread.currentThread().getName() + " - result from callable: " + s1);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                String s1 = myFutureTask.get(3, TimeUnit.SECONDS);
                log.info(Thread.currentThread().getName() + " - result from callable: " + s1);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
        }).start();
        String s1 = myFutureTask.get();
        log.info(Thread.currentThread().getName() + " - result from callable: " + s1);
    }
}
