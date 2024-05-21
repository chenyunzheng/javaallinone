package com.chris.allinone.spring.sampleapp.config;

import org.springframework.context.annotation.PropertySource;

/**
 * @author chrischen
 */
@PropertySource(value = {"classpath:static/a.properties", "file:/D:/cyzhope/code/non-project/allinone/"})
public class PropertySourceConfig {
}
