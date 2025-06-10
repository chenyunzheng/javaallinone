package com.chris.allinone.javacore.generic;

import java.util.Arrays;

/**
 * @author chrischen
 * @date 2025/6/10
 * @desc TODO描述主要功能
 */
public class Test {

    public static void main(String[] args) {
        Child1 child1 = new Child1();
        Arrays.stream(child1.getClass().getDeclaredMethods())
                .filter(m -> "setValue".equals(m.getName()))
                .forEach(System.out::println);
        System.out.println("排除 桥接方法");
        Arrays.stream(child1.getClass().getDeclaredMethods())
                .filter(m -> "setValue".equals(m.getName()) && !m.isBridge())
                .forEach(System.out::println);
    }
}
