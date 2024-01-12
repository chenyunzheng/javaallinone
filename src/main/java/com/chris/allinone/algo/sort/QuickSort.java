package com.chris.allinone.algo.sort;

import java.util.Arrays;

public class QuickSort {

    /** 双边排序 */
    static void quickSort(int[] a, int left, int right) {
        //递归终止条件
        if (left >= right) {
            return;
        }
        int comp = a[left];
        int i = left;
        int j = right;
        while (j > i) {
            //左移
            while (a[j] >= comp && j > i) {
                j--;
            }
            //右移
            while (a[i] <= comp && j > i) {
                i++;
            }
            if (j > i) {
                //交换
                int temp = a[j];
                a[j] = a[i];
                a[i] = temp;
            }

        }
        //双指针交汇
        if (i == j) {
            a[left] = a[i];
            a[i] = comp;
        }
        //分而治之
        quickSort(a, left, i - 1);
        quickSort(a, i + 1, right);
    }

    /** 单边排序 */
    //mark++

    public static void main(String[] args) {
        int[] a = {1, 3, 5, 6, 2, 7, 4, 0, 7, 4, 12, 435, 6, 783, 896, 46};
        quickSort(a, 0, a.length - 1);
        Arrays.stream(a).forEach(System.out::println);
    }
}
