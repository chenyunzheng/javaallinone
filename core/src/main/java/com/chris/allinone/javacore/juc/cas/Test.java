package com.chris.allinone.javacore.juc.cas;

import allinone.javacore.UnsafeFactory;
import jdk.internal.misc.Unsafe;

/**
 * @author chrischen
 */
public class Test {

    public static  int sum = 0;

    static Unsafe unsafe = UnsafeFactory.getUnsafe();
    static long offset;

    static {
        try {
            offset = unsafe.staticFieldOffset(Test.class.getField("sum"));
            System.out.println("sum offset = " + offset);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        int tNum = 10;

        for (int i = 0; i < tNum; i++) {
            new Thread(() -> {
                int j = 0;
                while (j < 10000) {
                    int expected;
                    //This operation has memory semantics of a volatile read and write
                    //CAS操作具有volatile语义，因此不必要求sum变量声明为volatile
                    do {
                        expected = sum;
                    } while (!unsafe.compareAndSetInt(Test.class, offset, expected, expected + 1));
//                    sum += 1;
                    j++;
                }
            }).start();
        }
        Thread.sleep(1000);
        System.out.println("sum = " + sum);
    }
}
