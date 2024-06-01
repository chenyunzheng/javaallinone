package com.andrew.vmybatis.spring;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author chrischen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(VMapperScanImportBeanDefinitionRegistrar.class)
public @interface VMapperScan {

    String value() default "";
}
