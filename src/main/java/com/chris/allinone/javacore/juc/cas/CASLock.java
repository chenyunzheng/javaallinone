package com.chris.allinone.javacore.juc.cas;

import com.chris.allinone.javacore.UnsafeFactory;
import jdk.internal.misc.Unsafe;

/**
 * @author chrischen
 * 可以将Lock抽象为接口，也就是AQS
 */
public class CASLock {

    private static final Unsafe UNSAFE = UnsafeFactory.getUnsafe();
    private static final long OFFSET = UnsafeFactory.getFieldOffset(UNSAFE, CASLock.class, "lockState");

    /**
     * 锁状态：0-开；1-关
     * volatile需要加，理由：UNSAFE.compareAndSwapInt()保证原子性、可见性，但只是针对此操作。
     */
    private volatile int lockState;

    public int getLockState() {
        return lockState;
    }

    public boolean lock() {
        assert UNSAFE != null;
        return UNSAFE.compareAndSetInt(this, OFFSET, 0, 1);
    }

    public void unlock() {
        this.lockState = 0;
    }
}
