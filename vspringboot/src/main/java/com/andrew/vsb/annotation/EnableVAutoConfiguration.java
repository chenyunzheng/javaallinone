package com.andrew.vsb.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author chrischen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
@Import(VAutoConfigurationImportSelector.class)
public @interface EnableVAutoConfiguration {
}
