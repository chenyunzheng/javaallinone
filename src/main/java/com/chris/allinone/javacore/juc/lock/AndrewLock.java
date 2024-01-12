package com.chris.allinone.javacore.juc.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author chrischen
 */
public class AndrewLock extends AbstractQueuedSynchronizer {

    public AndrewLock() {
        setState(0);
    }

    @Override
    protected boolean tryAcquire(int arg) {
        if (compareAndSetState(0, 1)) {
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    public void lock() {
        acquire(1);
    }

    public void unlock() {
        release(1);
    }
}
