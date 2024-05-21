package com.chris.allinone.javacore.juc.designpattern.avoidshare.thread_specific_storage;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.concurrent.ThreadSafe;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chrischen
 * 线程安全日期格式化工具类
 * SimpleDateFormat的hashCode被覆盖为pattern的hashCode，
 * 因此使用System.identityHashCode()获取近似内存地址
 */
@ThreadSafe
@Slf4j
public class SafeDateFormat {

    private static final ThreadLocal<SimpleDateFormat> VAR = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    public static String format(Date date) {
        System.out.println(Thread.currentThread().getName() + " -- " + VAR.get() + " -- " + System.identityHashCode(VAR.get()));
        String format = VAR.get().format(date);
        //VAR.remove();
        return format;
    }

    public static Date parse(String source) throws ParseException {
        System.out.println(Thread.currentThread().getName() + " -- " + VAR.get() + " -- " + System.identityHashCode(VAR.get()));
        Date date = VAR.get().parse(source);
        //VAR.remove();
        return date;
    }

    public static void main(String[] args) throws InterruptedException {
        int nThread = 10;
//        for (int i = 0; i < nThread; i++) {
//            new Thread(() -> SafeDateFormat.format(new Date())).start();
//        }

        //SimpleDateFormat在并发场景下问题复现
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        CountDownLatch countDownLatch = new CountDownLatch(1);
//        for (int i = 0; i < nThread; i++) {
//            new Thread(() -> {
//                try {
//                    countDownLatch.await();
//                    //log.info(simpleDateFormat.parse("2024-02-16 12:12:12").toString());
//                    log.info(VAR.get().parse("2024-02-16 12:12:12").toString());
//                } catch (InterruptedException | ParseException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//        countDownLatch.countDown();
        log.info("---------------------------------------------");
        //SimpleDateFormat在线程池场景下问题复现
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < nThread; i++) {
            executorService.execute(() -> {
                try {
                    //log.info(simpleDateFormat.parse("2024-02-16 12:12:12").toString());
                    log.info(VAR.get().parse("2024-02-16 12:12:12").toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
        }
        TimeUnit.SECONDS.sleep(2);
    }

}
