package com.chris.allinone.javacore.skills;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;

/**
 * @author chrischen
 * 1. 为验证方便，假定key范围1-1000
 * 2. 共10个线程并发执行累加
 * 3. 每个线程随机累加1000w次
 * Note: 流处理的parallel()如果使用，尽量同自定义的ForkJoinPoll一起
 */
@Slf4j
public class KeyCountUsingMap {

    private static final int THREAD_COUNT = 10;
    private static final int COUNT = 1000 * 10000;

    public static void main(String[] args) throws Exception {
        StopWatch stopWatch = new StopWatch();
//        stopWatch.start("basicUse");
//        Map<String, Long> basicUse = basicUse();
//        stopWatch.stop();
//        Assert.isTrue(basicUse.values().stream().reduce(0L, Long::sum) == COUNT, "basicUse error");
        stopWatch.start("normalUse");
        Map<String, LongAdder> normalUse = normalUse();
        stopWatch.stop();
        Assert.isTrue(normalUse.values().stream().mapToLong(LongAdder::longValue).sum() == COUNT, "normalUse error");
        stopWatch.start("bestPractice");
        Map<String, LongAdder> bestPractice = bestPractice();
        stopWatch.stop();
        Assert.isTrue(bestPractice.values().stream().mapToLong(LongAdder::longValue).sum() == COUNT, "bestPractice error");
        log.info(stopWatch.prettyPrint());
    }

    public static Map<String, Long> basicUse() throws InterruptedException, NoSuchAlgorithmException {
        final Map<String, Long> map = new HashMap<>();
        final Random random = SecureRandom.getInstanceStrong();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                synchronized (map) {
                    IntStream.rangeClosed(1, COUNT/THREAD_COUNT).forEach(value -> {
                        String key = "key-" + random.nextInt(1000);
                        if (map.get(key) == null) {
                            map.put(key, 1L);
                        } else {
                            map.put(key, map.get(key) + 1);
                        }
                    });
                }
            }).start();
        }
        TimeUnit.SECONDS.sleep(3);
        return map;
    }

    public static Map<String, LongAdder> normalUse() throws InterruptedException {
        final ConcurrentHashMap<String, LongAdder> concurrentHashMap = new ConcurrentHashMap<>();
        AtomicInteger counter = new AtomicInteger(0);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 10, 30, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), r -> new Thread(r, "AC-" + counter.getAndIncrement()));
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPoolExecutor.execute(() -> {
                IntStream.rangeClosed(1, COUNT/THREAD_COUNT).forEach(value -> {
                    //log.info(String.valueOf(value));
                    String key = "key-" + ThreadLocalRandom.current().nextInt(1000);
                    concurrentHashMap.computeIfAbsent(key, s -> new LongAdder()).increment();
                });
            });
        }
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS);
        return concurrentHashMap;
    }

    public static Map<String, LongAdder> bestPractice() throws InterruptedException {
        //使用ForkJoinPool + parallel()
        final ConcurrentHashMap<String, LongAdder> concurrentHashMap = new ConcurrentHashMap<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> {
            IntStream.rangeClosed(1, COUNT).parallel().forEach(value -> {
                //log.info(String.valueOf(value));
                String key = "key-" + ThreadLocalRandom.current().nextInt(1000);
                concurrentHashMap.computeIfAbsent(key, s -> new LongAdder()).increment();
            });
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(60, TimeUnit.SECONDS);
        return concurrentHashMap;
    }

    static class Counter implements Runnable {

        private final Map<String, LongAdder> map;

        public Counter(Map<String, LongAdder> map) {
            this.map = map;
        }

        @Override
        public void run() {
            if (map instanceof ConcurrentHashMap) {
                IntStream.rangeClosed(1, COUNT).forEach(value -> {
                    log.info(String.valueOf(value));
                    String key = "key-" + ThreadLocalRandom.current().nextInt(1000);
                    map.computeIfAbsent(key, s -> new LongAdder()).increment();
                });
            } else {
                synchronized (map) {
                    IntStream.rangeClosed(1, COUNT).forEach(value -> {
                        log.info(String.valueOf(value));
                        String key = "key-" + ThreadLocalRandom.current().nextInt(1000);
                        //可能存在线程安全问题
                        map.computeIfAbsent(key, s -> new LongAdder()).increment();
                    });
                }
            }
        }
    }

    public static void demo() throws InterruptedException {
        final ConcurrentHashMap<String, LongAdder> concurrentHashMap = new ConcurrentHashMap<>();
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> {
            //IntStream.rangeClosed(1, COUNT).parallel()会使用自定义的ForkJoinPool，而不是ForkJoinPool.commonPool
            IntStream.rangeClosed(1, COUNT).parallel().forEach(value -> {
                log.info(String.valueOf(value));
                String key = "key-" + ThreadLocalRandom.current().nextInt(1000);
                concurrentHashMap.computeIfAbsent(key, s -> new LongAdder()).increment();
            });
        });
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(10, TimeUnit.SECONDS);

        AtomicInteger counter = new AtomicInteger(0);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                5, 5, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(20),
                r -> new Thread(r, "AC-" + counter.getAndIncrement()));
        threadPoolExecutor.execute(() -> {
            //自定义线程池threadPoolExecutor执行任务IntStream.rangeClosed(1, COUNT).parallel()，
            //并不完全使用自定义线程池，还有ForkJoinPool.commonPool的掺和
        });
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS);

        //获取累加总和
        //long sum = concurrentHashMap.values().stream().mapToLong(LongAdder::longValue).sum();
        long sum = concurrentHashMap.reduceValuesToLong(200, value -> value.longValue(), 0L, Long::sum);
        Assert.isTrue(COUNT == sum, "error");
    }

}
