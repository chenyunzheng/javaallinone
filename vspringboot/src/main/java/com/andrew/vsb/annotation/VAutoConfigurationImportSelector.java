package com.andrew.vsb.annotation;

import com.andrew.vsb.AutoConfiguration;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * @author chrischen
 */
public class VAutoConfigurationImportSelector implements DeferredImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        List<String> selectImports = new ArrayList<>();
        ServiceLoader<AutoConfiguration> autoConfigurations = ServiceLoader.load(AutoConfiguration.class);
        for (AutoConfiguration autoConfiguration : autoConfigurations) {
            selectImports.add(autoConfiguration.getClass().getName());
        }
        return selectImports.toArray(new String[0]);
    }
}
