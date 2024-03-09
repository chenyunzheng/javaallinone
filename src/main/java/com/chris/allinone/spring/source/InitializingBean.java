package com.chris.allinone.spring.source;

/**
 * @author chrischen
 */
public interface InitializingBean {

    /**
     * <p>This method allows the bean instance to perform validation of its overall
     * configuration and final initialization when all bean properties have been set.
     * */
    void afterPropertiesSet();
}
