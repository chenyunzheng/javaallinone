package com.chris.allinone.spring.source;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author chrischen
 */
@Data
@AllArgsConstructor
public class BeanDefinition {

    private String name;
    private String scope;
    private Class<?> clazz;

}
