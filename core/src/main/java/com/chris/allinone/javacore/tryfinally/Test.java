package com.chris.allinone.javacore.tryfinally;

/**
 * @author chrischen
 * @date 2025/2/23
 * @desc TODO描述主要功能
 */
public class Test {
    public static void main(String[] args) {
        System.out.println(count());
    }

    private static int count() {
        try {
            return 1;
        } catch (Exception e) {
            System.out.println("exception");
            return 2;
        } finally {
            System.out.println("finally");
//            return 3;
        }
    }
}
