package com.chris.allinone.javacore.threadpool.queuelast;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author chrischen
 */
public class QueueLastLinkedBlockingQueue<E> extends LinkedBlockingQueue<E> {

    public QueueLastLinkedBlockingQueue(int capacity) {
        super(capacity);
    }

    @Override
    public boolean offer(E e) {
        if (Objects.isNull(e)) {
            throw new NullPointerException();
        }
        return false;
    }
}
