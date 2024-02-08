package com.chris.allinone.algo.string;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author chrischen
 * 1. 挖掘匹配串target的内部信息
 * 2. i不回溯
 */
public class KMPMatch {

    public static void main(String[] args) {
        String source = "ABABCDABAB";
        KMPMatch kmpMatch = new KMPMatch();
        int[] next = kmpMatch.kmpMaxCommonPresuffix(source);
        String s = Arrays.stream(next).mapToObj(String::valueOf).collect(Collectors.joining(",", "[", "]"));
        System.out.println(s);
        int index = kmpMatch.kmpSearch(source, "BA");
        System.out.println("首次匹配的主串index = " + index);

    }

    /**
     * 难想到。。。
     * i不回溯，j回溯，需要确定j有效位置
     * 已匹配为子串【0，j-1】和主串【i-j，i-1】，若首尾字符相等，则j无需从0开始
     * 转换为：寻找子串中最大公共前后缀字符串长度
     * https://blog.csdn.net/qq_62982856/article/details/128003067
     * 算法复杂度：
     * @return
     */
    public int[] kmpMaxCommonPresuffix(String target) {
        Objects.requireNonNull(target);
        char[] tChars = target.toCharArray();
        int[] lens = new int[tChars.length];
        lens[0] = 0;
        /*
         * len用于记录当前子串的最长公共前后缀长度
         */
        int len = 0;
        //从第二个字符开始遍历，求[0,i]的子串的最长公共前后缀长度
        for (int i = 1; i < tChars.length; i++) {
            if (tChars[len] == tChars[i]) {
                //在上一子串的基础上拼接
                len++;
                lens[i] = len;
            } else {
                //不能在上一子串的最长公共前后缀上拼接！！而是使用上一子串的最长公共前后缀的最长公共前后缀
                //todo ?
                if (len == 0) {
                    lens[i] = 0;
                } else {
                    len = lens[len - 1];
                    i = i - 1;
                }
            }
        }
        return lens;
    }

    /**
     * 返回首次匹配的主串index
     * @param source
     * @param target
     * @return
     */
    public int kmpSearch(String source, String target) {
        if (source == null || target == null) {
            return -1;
        }
        if (source.length() < target.length()) {
            return -1;
        }
        int[] next = kmpMaxCommonPresuffix(target);
        char[] sChars = source.toCharArray();
        char[] tChars = target.toCharArray();
        int i = 0, j = 0;
        while (i < sChars.length && j < tChars.length) {
            if (sChars[i] == tChars[j]) {
                i++;
                j++;
            } else {
                if (j == 0) {
                    i++;
                } else {
                    j = next[j-1];
                }
            }
        }
        if (j == tChars.length) {
            return i - j;
        }
        return -1;
    }


}
