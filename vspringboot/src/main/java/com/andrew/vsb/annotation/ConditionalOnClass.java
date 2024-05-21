package com.andrew.vsb.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * @author chrischen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
@Conditional(OnClassCondition.class)
public @interface ConditionalOnClass {

    String value() default "";
}
