package com.andrew.vmybatis.spring;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;



/**
 * @author chrischen
 */
public class VMapperScanClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public VMapperScanClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            //BeanDefinition beanDefinition = beanDefinitionHolder.getBeanDefinition();
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            String mapperInterface = beanDefinition.getBeanClassName();
            beanDefinition.setBeanClassName(VMapperFactoryBean.class.getName());
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(mapperInterface);
            //GenericBeanDefinition
            beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
        return beanDefinitionHolders;
    }

//    @Override
//    protected boolean isCandidateComponent(MetadataReader metadataReader) throws IOException {
//        return true;
//    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        return metadata.isInterface();
    }
}
