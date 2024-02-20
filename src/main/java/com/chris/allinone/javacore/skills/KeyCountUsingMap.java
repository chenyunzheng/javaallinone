package com.chris.allinone.javacore.skills;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * @author chrischen
 * 1. 为验证方便，假定key范围1-1000
 * 2. 共10个线程并发执行累加
 * 3. 每个线程随机累加1000w次
 */
public class KeyCountUsingMap {

    private static final int THREAD_COUNT = 10;
    private static final int COUNT = 1000 * 10000;

    public static void main(String[] args) {
        final ConcurrentHashMap<String, Long> concurrentHashMap = new ConcurrentHashMap<>();
        IntStream
    }

}
