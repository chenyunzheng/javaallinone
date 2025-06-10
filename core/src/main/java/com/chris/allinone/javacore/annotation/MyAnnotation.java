package com.chris.allinone.javacore.annotation;

import java.lang.annotation.*;

/**
 * @author chrischen
 * @date 2025/6/10
 * @desc TODO描述主要功能
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Inherited
public @interface MyAnnotation {
    String value() default "";
}
