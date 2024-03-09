package com.chris.allinone.spring.source;

import lombok.SneakyThrows;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;

/**
 * @author chrischen
 */
public class ApplicationContext {

    private final Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();
    private final Map<String, Object> singletonObjectMap = new HashMap<>();
    private final List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public ApplicationContext(Class<?> clazz, String[] args) {

        if (!clazz.isAnnotationPresent(ComponentScan.class)) {
            return;
        }
        //scan: BeanDefinition
        ComponentScan componentScan = clazz.getAnnotation(ComponentScan.class);
        String value = componentScan.value();
        URL resource = getClass().getClassLoader().getResource(value.replace(".", "/"));
        File file = new File(Objects.requireNonNull(resource).getPath());
        if (file.isDirectory()) {
            List<File> classFileList = getAllClassFileList(file);
            for (File classFile : classFileList) {
                String path = classFile.getPath();
                String dotClass = path.substring(path.lastIndexOf(value.replace(".", "\\")), path.length() - ".class".length());
                dotClass = dotClass.replace("\\", ".");
                //check @Component
                Class<?> aClass = null;
                try {
                    aClass = Class.forName(dotClass);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (Objects.nonNull(aClass) && aClass.isAnnotationPresent(Component.class)) {
                    Component componentAnnotation = aClass.getAnnotation(Component.class);
                    String componentValue = componentAnnotation.value();
                    String defaultValue = Introspector.decapitalize(dotClass.substring(dotClass.lastIndexOf(".") + 1));
                    componentValue = componentValue.length() == 0 ? defaultValue : componentValue;
                    String scope = "singleton";
                    if (aClass.isAnnotationPresent(Scope.class)) {
                        Scope scopeAnnotation = aClass.getAnnotation(Scope.class);
                        String scopeValue = scopeAnnotation.value();
                        if (scopeValue.length() != 0) {
                            scope = scopeValue;
                        }
                    }
                    BeanDefinition beanDefinition = new BeanDefinition(componentValue, scope, aClass);
                    beanDefinitionMap.put(componentValue, beanDefinition);
                }
            }
        }
        //make singletonObjectMap && get beanpostprocessor list
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String componentName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if ("singleton".equals(beanDefinition.getScope())) {
                try {
                    Object instance = createBean(beanDefinition);
                    singletonObjectMap.put(componentName, instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Class<?> definitionClazz = beanDefinition.getClazz();
            if (BeanPostProcessor.class.isAssignableFrom(definitionClazz)) {
                Object beanPostProcessor = singletonObjectMap.computeIfAbsent(componentName, s -> createBean(beanDefinition));
                beanPostProcessorList.add((BeanPostProcessor) beanPostProcessor);
            }
        }
    }

    private List<File> getAllClassFileList(File directory) {
        List<File> fileList = new ArrayList<>();
        File[] files = Optional.ofNullable(directory.listFiles()).orElse(new File[0]);
        Arrays.stream(files).forEach(file -> {
            if (file.isDirectory()) {
                List<File> subFileList = getAllClassFileList(file);
                if (!subFileList.isEmpty()) {
                    fileList.addAll(subFileList);
                }
            } else {
                if (file.getName().endsWith(".class")) {
                    fileList.add(file);
                }
            }
        });
        return fileList;
    }

    public Object getBean(String beanName) {
        Object result = null;
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                result = singletonObjectMap.computeIfAbsent(beanName, s -> createBean(beanDefinition));
            } else {
                result = createBean(beanDefinition);
            }
        }
        return result;
    }

    @SneakyThrows
    private Object createBean(BeanDefinition beanDefinition) {
        //实例化
        Class<?> clazz = beanDefinition.getClazz();
        Object instance = clazz.getConstructor().newInstance();
        //依赖注入
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Autowired.class)) {
                Autowired autowiredAnnotation = field.getAnnotation(Autowired.class);
                String autowiredValue = autowiredAnnotation.value();
                if (autowiredValue.length() == 0) {
                    String typeName = field.getType().getName();
                    autowiredValue = Introspector.decapitalize(typeName.substring(typeName.lastIndexOf(".") + 1));
                }
                Object autowiredBean = getBean(autowiredValue);
                field.setAccessible(true);
                field.set(instance, autowiredBean);
            }
        }
        //初始化前(类似其他属性的设置)
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanDefinition.getName());
        }
        //初始化(afterPropertiesSet)
        if (instance instanceof InitializingBean) {
            ((InitializingBean)instance).afterPropertiesSet();
        }
        //初始化后(类似AOP的完成)
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
            instance = beanPostProcessor.postProcessAfterInitialization(instance, beanDefinition.getName());
        }
        return instance;
    }
}
