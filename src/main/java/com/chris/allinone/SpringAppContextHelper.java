package com.chris.allinone;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chrischen
 */
@Component
public class SpringAppContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringAppContextHelper.applicationContext = applicationContext;
    }

    public static ApplicationContext getAppContext() {
        return SpringAppContextHelper.applicationContext;
    }
}
