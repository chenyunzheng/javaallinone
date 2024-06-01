package com.andrew.vmybatis.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.util.Map;

/**
 * @author chrischen
 */
public class VMapperScanImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //获取mapper扫描路径
        Map<String, Object> annotationAttributes = importingClassMetadata.getAnnotationAttributes(VMapperScan.class.getName());
        String mapperLocation = (String)annotationAttributes.get("value");
        //利用spring进行扫描得到接口的BeanDefinition
        VMapperScanClassPathBeanDefinitionScanner beanDefinitionScanner = new VMapperScanClassPathBeanDefinitionScanner(registry);
        //代替对isCandidateComponent(MetadataReader metadataReader)的重写
        beanDefinitionScanner.addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                return true;
            }
        });
        beanDefinitionScanner.scan(mapperLocation);
    }
}
