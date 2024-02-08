package com.chris.allinone.algo.string;

/**
 * @author chrischen
 * 从source串中找出target串并返回位置
 */
public class NativeMatch {

    /**
     * i -> 遍历主串，不回溯
     * j -> 内循环，每次从0开始遍历
     * k -> 临时配合j在主串上移动
     * 算法复杂度：O((s - t + 1) * t)
     * @param source
     * @param target
     * @return
     */
    public int solution1(String source, String target) {
        if (source == null || target == null) {
            return -1;
        }
        if (source.length() < target.length()) {
            return -1;
        }
        char[] sChars = source.toCharArray();
        char[] tChars = target.toCharArray();
        for (int i = 0; i < sChars.length - tChars.length + 1; i++) {
            int k = i;
            int j = 0;
            for (; j < tChars.length; j++, k++) {
                if (sChars[k] != tChars[j]) {
                    break;
                }
            }
            if (j == tChars.length) {
                return i;
            }
        }
        return -1;
    }

    /**
     * solution1简化版，不需要额外标记k
     * @param source
     * @param target
     * @return
     */
    public int solution2(String source, String target) {
        if (source == null || target == null) {
            return -1;
        }
        if (source.length() < target.length()) {
            return -1;
        }
        char[] sChars = source.toCharArray();
        char[] tChars = target.toCharArray();
        int i = 0, j = 0;
        while (i < sChars.length && j < tChars.length) {
            if (sChars[i] == tChars[j]) {
                i++;
                j++;
            } else {
                //回退j再加1
                i = i - j + 1;
                j = 0;
                //剩余比较次数小于tChars.length，不再比较
                if (i >= sChars.length - tChars.length + 1) {
                    break;
                }
            }
        }
        if (j == tChars.length) {
            return i - j;
        }
        return -1;
    }

    public static void main(String[] args) {
        String source = "abcdex";
        String target = "dec";
        NativeMatch nativeMatch = new NativeMatch();
        System.out.println("solution1: " + nativeMatch.solution1(source, target));
        System.out.println("solution2: " + nativeMatch.solution2(source, target));
    }
}
