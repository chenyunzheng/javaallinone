package com.andrew.middleware.nacos.task.engine;

import com.andrew.middleware.nacos.exception.NacosException;
import com.andrew.middleware.nacos.executor.ExecutorFactory;
import com.andrew.middleware.nacos.executor.NameThreadFactory;
import com.andrew.middleware.nacos.task.AbstractDelayTask;
import com.andrew.middleware.nacos.task.NacosTaskProcessor;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Nacos delay task execute engine.
 *
 * @author xiweng.yy
 */
public class NacosDelayTaskExecuteEngine extends AbstractNacosTaskExecuteEngine<AbstractDelayTask> {
    
    private final ScheduledExecutorService processingExecutor;
    
    protected final ConcurrentHashMap<Object, AbstractDelayTask> tasks;
    
    protected final ReentrantLock lock = new ReentrantLock();
    
    public NacosDelayTaskExecuteEngine(String name) {
        this(name, null);
    }
    
    public NacosDelayTaskExecuteEngine(String name, Logger logger) {
        this(name, 32, logger, 100L);
    }
    
    public NacosDelayTaskExecuteEngine(String name, Logger logger, long processInterval) {
        this(name, 32, logger, processInterval);
    }
    
    public NacosDelayTaskExecuteEngine(String name, int initCapacity, Logger logger) {
        this(name, initCapacity, logger, 100L);
    }
    
    public NacosDelayTaskExecuteEngine(String name, int initCapacity, Logger logger, long processInterval) {
        super(logger);
        tasks = new ConcurrentHashMap<>(initCapacity);
        processingExecutor = ExecutorFactory.newSingleScheduledExecutorService(new NameThreadFactory(name));
        processingExecutor
                .scheduleWithFixedDelay(new ProcessRunnable(), processInterval, processInterval, TimeUnit.MILLISECONDS);
    }
    
    @Override
    public int size() {
        lock.lock();
        try {
            return tasks.size();
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public boolean isEmpty() {
        lock.lock();
        try {
            return tasks.isEmpty();
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public AbstractDelayTask removeTask(Object key) {
        lock.lock();
        try {
            AbstractDelayTask task = tasks.get(key);
            if (null != task && task.shouldProcess()) {
                return tasks.remove(key);
            } else {
                return null;
            }
        } finally {
            lock.unlock();
        }
    }
    
    @Override
    public Collection<Object> getAllTaskKeys() {
        Collection<Object> keys = new HashSet<>();
        lock.lock();
        try {
            keys.addAll(tasks.keySet());
        } finally {
            lock.unlock();
        }
        return keys;
    }
    
    @Override
    public void shutdown() throws NacosException {
        tasks.clear();
        processingExecutor.shutdown();
    }
    
    @Override
    public void addTask(Object key, AbstractDelayTask newTask) {
        lock.lock();
        try {
            AbstractDelayTask existTask = tasks.get(key);
            if (null != existTask) {
                newTask.merge(existTask);
            }
            tasks.put(key, newTask);
        } finally {
            lock.unlock();
        }
    }
    
    /**
     * process tasks in execute engine.
     */
    protected void processTasks() {
        Collection<Object> keys = getAllTaskKeys();
        for (Object taskKey : keys) {
            AbstractDelayTask task = removeTask(taskKey);
            if (null == task) {
                continue;
            }
            NacosTaskProcessor processor = getProcessor(taskKey);
            if (null == processor) {
                getEngineLog().error("processor not found for task, so discarded. " + task);
                continue;
            }
            try {
                // ReAdd task if process failed
                if (!processor.process(task)) {
                    retryFailedTask(taskKey, task);
                }
            } catch (Throwable e) {
                getEngineLog().error("Nacos task execute error ", e);
                retryFailedTask(taskKey, task);
            }
        }
    }
    
    private void retryFailedTask(Object key, AbstractDelayTask task) {
        task.setLastProcessTime(System.currentTimeMillis());
        addTask(key, task);
    }
    
    private class ProcessRunnable implements Runnable {
        
        @Override
        public void run() {
            try {
                processTasks();
            } catch (Throwable e) {
                getEngineLog().error(e.toString(), e);
            }
        }
    }
}
