package com.chris.allinone.javacore.exception;

/**
 * @author chrischen
 */
public class Test {

    public static void a() throws AnCheckedException {
        throw new AnCheckedException("an checked exception");
    }

    public static void main(String[] args) {
        try {
            Test.a();
        } catch (AnCheckedException e) {
            e.printStackTrace();
        }
    }
}
