package com.andrew.middleware.nacos.task.engine;

import com.andrew.middleware.nacos.exception.NacosException;
import com.andrew.middleware.nacos.lifecycle.Closeable;
import com.andrew.middleware.nacos.task.AbstractExecuteTask;
import com.andrew.middleware.nacos.task.NacosTask;
import com.andrew.middleware.nacos.task.NacosTaskProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Nacos execute task execute worker.
 *
 * @author xiweng.yy
 */
public final class TaskExecuteWorker implements NacosTaskProcessor, Closeable {
    
    /**
     * Max task queue size 32768.
     */
    private static final int QUEUE_CAPACITY = 1 << 15;
    
    private final Logger log;
    
    private final String name;
    
    private final BlockingQueue<Runnable> queue;
    
    private final AtomicBoolean closed;
    
    private final InnerWorker realWorker;
    
    public TaskExecuteWorker(final String name, final int mod, final int total) {
        this(name, mod, total, null);
    }
    
    public TaskExecuteWorker(final String name, final int mod, final int total, final Logger logger) {
        this.name = name + "_" + mod + "%" + total;
        this.queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);
        this.closed = new AtomicBoolean(false);
        this.log = null == logger ? LoggerFactory.getLogger(TaskExecuteWorker.class) : logger;
        realWorker = new InnerWorker(this.name);
        realWorker.start();
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public boolean process(NacosTask task) {
        if (task instanceof AbstractExecuteTask) {
            putTask((Runnable) task);
        }
        return true;
    }
    
    private void putTask(Runnable task) {
        try {
            queue.put(task);
        } catch (InterruptedException ire) {
            log.error(ire.toString(), ire);
        }
    }
    
    public int pendingTaskCount() {
        return queue.size();
    }
    
    /**
     * Worker status.
     */
    public String status() {
        return name + ", pending tasks: " + pendingTaskCount();
    }
    
    @Override
    public void shutdown() throws NacosException {
        queue.clear();
        closed.compareAndSet(false, true);
        realWorker.interrupt();
    }
    
    /**
     * Inner execute worker.
     */
    private class InnerWorker extends Thread {
        
        InnerWorker(String name) {
            setDaemon(false);
            setName(name);
        }
        
        @Override
        public void run() {
            while (!closed.get()) {
                try {
                    Runnable task = queue.take();
                    long begin = System.currentTimeMillis();
                    task.run();
                    long duration = System.currentTimeMillis() - begin;
                    if (duration > 1000L) {
                        log.warn("task {} takes {}ms", task, duration);
                    }
                } catch (Throwable e) {
                    log.error("[TASK-FAILED] " + e, e);
                }
            }
        }
    }
}
