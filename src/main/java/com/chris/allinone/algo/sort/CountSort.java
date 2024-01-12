package com.chris.allinone.algo.sort;

public class CountSort {

    public static void sort(int[] a) {
        int[] array = new int[10];
        for (int i = 0; i < a.length; i++) {
            array[a[i]] += 1;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i]; j++) {
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 6, 2, 7, 4, 0, 7, 4, 9, 5};
        CountSort.sort(a);
    }
}
