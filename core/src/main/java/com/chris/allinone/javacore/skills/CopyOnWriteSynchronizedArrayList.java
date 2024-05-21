package com.chris.allinone.javacore.skills;


import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * @author chrischen
 * 对比线程安全的CopyOnWriteArrayList和Synchronized ArrayList适用场景
 */
@Slf4j
public class CopyOnWriteSynchronizedArrayList {

    public static void main(String[] args) {
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        int writeCount = 10 * 10000;
        StopWatch stopWatch = new StopWatch();
        //写多读少并发场景：模拟10w次写
        stopWatch.start("Write: copyOnWriteArrayList");
        IntStream.rangeClosed(1, writeCount).parallel().forEach(copyOnWriteArrayList::add);
        stopWatch.stop();
        Assert.isTrue(copyOnWriteArrayList.size() == writeCount, "error");
        stopWatch.start("Write: synchronizedList");
        IntStream.rangeClosed(1, writeCount).parallel().forEach(synchronizedList::add);
        stopWatch.stop();
        Assert.isTrue(synchronizedList.size() == writeCount, "error");
        log.info(stopWatch.prettyPrint());
        //读多写少并发场景：模拟1000w次读
        int readCount = 1000 * 10000;
        stopWatch.start("Read: copyOnWriteArrayList");
        IntStream.range(0, readCount).parallel().forEach(value -> copyOnWriteArrayList.get(value >>> 10));
        stopWatch.stop();
        stopWatch.start("Read: synchronizedList");
        IntStream.range(0, readCount).parallel().forEach(value -> synchronizedList.get(value >>> 10));
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());
    }
}
