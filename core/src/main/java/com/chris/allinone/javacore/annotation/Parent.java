package com.chris.allinone.javacore.annotation;

/**
 * @author chrischen
 * @date 2025/6/10
 * @desc TODO描述主要功能
 */
@MyAnnotation(value = "Parent class")
public class Parent {

    @MyAnnotation(value = "Parent method")
    public void method() {

    }
}
