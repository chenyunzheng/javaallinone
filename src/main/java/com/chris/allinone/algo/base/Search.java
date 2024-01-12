package com.chris.allinone.algo.base;

public class Search {

    //二分查找（前提有序）
    public static int binarySearch(int[] values, int low, int high, int target) {
        int mid = (low + high) / 2;
        if (low == mid || mid == high) {
            if (values[mid] != target) {
                throw new IllegalStateException("no value found");
            }
        }
        if (values[mid] == target) {
            return mid;
        } else if (values[mid] > target) {
            return binarySearch(values, low, mid, target);
        } else {
            return binarySearch(values, mid, high, target);
        }
    }

    public static void main(String[] args) {
        int[] values = {11, 22, 23, 45, 56, 78, 333, 555, 663, 992};
        int low = 0;
        int high = values.length - 1;
        int target = 11;
        System.out.println(binarySearch(values, low, high, target));
    }
}
