package com.chris.allinone.spring.skills;

import com.chris.allinone.spring.skills.pojo.Account;
import org.springframework.beans.factory.SmartFactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author chrischen
 */
@Component
public class AndrewFactoryBean implements SmartFactoryBean<Account> {

    @Override
    public Account getObject() throws Exception {
        return new Account();
    }

    @Override
    public Class<?> getObjectType() {
        return Account.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public boolean isPrototype() {
        return false;
    }

    //是否EagerInit
    @Override
    public boolean isEagerInit() {
        return true;
    }
}
