package com.chris.allinone.javacore.generic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chrischen
 * @date 2025/6/10
 * @desc Parent
 */
public class Parent<T> {
    //用于记录value更新的次数，模拟日志记录的逻辑
    private AtomicInteger updateCount = new AtomicInteger(0);
    private T value;

    public void setValue(T value) {
        this.value = value;
        updateCount.getAndIncrement();
    }

    @Override
    public String toString() {
        return String.format("value: %s updateCount: %d", value, updateCount.get());
    }
}
