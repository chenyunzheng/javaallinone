package com.andrew.vsb.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

/**
 * @author chrischen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@Documented
@ComponentScan
@Configuration
@EnableVAutoConfiguration
public @interface VSpringBootApplication {
}
