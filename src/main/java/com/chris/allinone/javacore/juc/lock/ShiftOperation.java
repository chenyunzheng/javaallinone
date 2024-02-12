package com.chris.allinone.javacore.juc.lock;

import java.lang.annotation.Native;

/**
 * @author chrischen
 * 位移运算
 */
public class ShiftOperation {

    /**
     * A constant holding the minimum value an {@code int} can
     * have, -2<sup>31</sup>.
     */
    @Native
    public static final int   MIN_VALUE = 0x80000000;
    /**
     * A constant holding the maximum value an {@code int} can
     * have, 2<sup>31</sup>-1.
     */
    @Native public static final int   MAX_VALUE = 0x7fffffff;

    /*
     * Read vs write count extraction constants and functions.
     * Lock state is logically divided into two unsigned shorts:
     * The lower one representing the exclusive (writer) lock hold count,
     * and the upper the shared (reader) hold count.
     */

    static final int SHARED_SHIFT   = 16;
    static final int SHARED_UNIT    = (1 << SHARED_SHIFT);
    static final int MAX_COUNT      = (1 << SHARED_SHIFT) - 1;
    static final int EXCLUSIVE_MASK = (1 << SHARED_SHIFT) - 1;

    /** Returns the number of shared holds represented in count. */
    static int sharedCount(int c)    { return c >>> SHARED_SHIFT; }
    /** Returns the number of exclusive holds represented in count. */
    static int exclusiveCount(int c) { return c & EXCLUSIVE_MASK; }

    /*
        >> ：算术右移运算符，也称带符号右移。用最高位填充移位后左侧的空位。
        >>>：逻辑右移运算符，也称无符号右移。只对位进行操作，用0填充左侧的空位。
    */

    public static void main(String[] args) {
        System.out.println(MIN_VALUE + MIN_VALUE);
        System.out.println(MAX_VALUE + 1 + MAX_VALUE + 1);

        int a = 0xffffffff;
        int b = 0x7fffffff;
        System.out.println((-1 >> 1) == a);
        System.out.println((-1 >>> 1) == b);
    }
}
