package com.andrew.vsb.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

/**
 * @author chrischen
 */
public class OnClassCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionalOnClass.class.getName());
        String value = (String) attributes.getOrDefault("value", "");
        try {
            context.getClassLoader().loadClass(value);
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
