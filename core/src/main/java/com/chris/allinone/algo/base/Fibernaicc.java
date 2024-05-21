package com.chris.allinone.algo.base;

public class Fibernaicc {

    static int f0 = 0;
    static int f1 = 1;

    //fn = fn_1 + fn_2
    public static void main(String[] args) {
        int n = 7;
        int result = faicc(n);
        System.out.println(result);
    }

    private static int faicc(int n) {
        //终止条件
        if (n == 0) {
            return f0;
        }
        if (n == 1) {
            return f1;
        }
        //功能 + 递归
        return faicc(n - 1) + faicc(n - 2);
    }
}
