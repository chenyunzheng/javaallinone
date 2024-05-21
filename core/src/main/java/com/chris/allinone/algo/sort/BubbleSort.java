package com.chris.allinone.algo.sort;

import java.util.Arrays;

public class BubbleSort {

    /**
     * 冒泡排序
     * 每一轮完成最大上浮，O(n^2)
     * @param nums
     */
    public static void sort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 4, 23, 1, 2, 434, 534, 56, 3, 664, 78, 44, 6492, 502};
        BubbleSort.sort(nums);
        Arrays.stream(nums).forEach(System.out::println);
    }
}
