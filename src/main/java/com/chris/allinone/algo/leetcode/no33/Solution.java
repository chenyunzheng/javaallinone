package com.chris.allinone.algo.leetcode.no33;

/**
 * @author chrischen
 * [33]搜索旋转排序数组
 */
public class Solution {

    public int search(int[] nums, int target) {
        int low = 0;
        int high = nums.length - 1;
        while (high >= low) {
            int mid = (low + high) % 2 == 0 ? (low + high) / 2 : (low + high) / 2 + 1;
            //终止条件
            if (high == low && nums[mid] != target) {
                return -1;
            }
            if (high == low + 1 && nums[mid] != target) {
                return nums[low] != target ? -1 : low;
            }

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] > target) {
                if (target >= nums[low]) {
                    high = mid;
                } else {
                    //fmid, flow比较
                    if (nums[mid] > nums[low]) {
                        low = mid + 1;
                    } else {
                        low = 0;
                        high = mid - 1;
                    }
                }
            } else {
                if (target <= nums[high]) {
                    low = mid;
                } else {
                    //fmid, fhigh比较
                    if (nums[mid] > nums[high]) {
                        low = mid + 1;
                    } else {
                        low = 0;
                        high = mid - 1;
                    }
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
//        int[] nums = {4,5,6,7,0,1,2};
//        int target = 6;
//        int[] nums = {1,3};
//        int target = 3;
//        int[] nums = {1};
//        int target = 7;
//        int[] nums = {4,5,6,7,8,1,2,3};
//        int target = 9;
        int[] nums = {4,5,6,7,0,1,2};
        int target = 1;
//        int[] nums = {7,8,1,2,3,4,5,6};
//        int target = 6;
        System.out.println(new Solution().search(nums, target));
    }
}
