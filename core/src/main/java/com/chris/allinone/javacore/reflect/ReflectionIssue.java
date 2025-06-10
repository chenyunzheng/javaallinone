package com.chris.allinone.javacore.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author chrischen
 * @date 2025/6/10
 * @desc ReflectionIssue
 */
public class ReflectionIssue {

    public void age(int age) {
        System.out.println("int age: " + age);
    }

    public void age(Integer age) {
        System.out.println("Integer age: " + age);
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Method declaredMethod = ReflectionIssue.class.getDeclaredMethod("age", Integer.TYPE); //int age: 18
        Method declaredMethod = ReflectionIssue.class.getDeclaredMethod("age", Integer.class); //Integer age: 18 √
        declaredMethod.invoke(new ReflectionIssue(), 18);
    }
}
