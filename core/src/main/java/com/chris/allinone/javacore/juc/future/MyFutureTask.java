package com.chris.allinone.javacore.juc.future;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * @author chrischen
 * 对FutureTask简版实现
 * 面向接口
 */
@Slf4j
public class MyFutureTask<V> implements Runnable, Future<V> {

    private final Callable<V> callable;
    private volatile V result;
    private final List<Thread> parkList = new Vector<>();

    public MyFutureTask(Callable<V> callable) {
        Objects.requireNonNull(callable);
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            V res = callable.call();
            this.result = res;
            parkList.forEach(LockSupport::unpark);
        } catch (Exception e) {
            log.error("exception", e);
        }
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return this.result != null;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        if (this.result != null) {
            return this.result;
        }
        Thread current = Thread.currentThread();
        this.parkList.add(current);
        LockSupport.park();
        return this.result;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        long end = System.nanoTime() + unit.toNanos(timeout);
        do {
            if (this.result != null) {
                return this.result;
            }
        } while (System.nanoTime() < end);
        return this.result;
    }
}
