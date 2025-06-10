package com.chris.allinone.javacore.annotation;

import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;

/**
 * @author chrischen
 * @date 2025/6/10
 * @desc TODO描述主要功能
 */
public class Child extends Parent {

    @Override
    public void method() {
        super.method();
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Child c = new Child();
        MyAnnotation annotation = c.getClass().getAnnotation(MyAnnotation.class);
        System.out.println("MyAnnotation on Child class: " + annotation);
        Method declaredMethod = c.getClass().getDeclaredMethod("method");
        MyAnnotation methodAnnotation = declaredMethod.getAnnotation(MyAnnotation.class);
        System.out.println("MyAnnotation on Child method: " + methodAnnotation);
        MyAnnotation myAnnotation = AnnotatedElementUtils.findMergedAnnotation(declaredMethod, MyAnnotation.class);
        System.out.println("MyAnnotation on Child method found by AnnotatedElementUtils: " + myAnnotation);
    }
}
