package com.chris.allinone.spring.skills.service;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

/**
 * @author chrischen
 */
@Component
public class BillService {

    @Lookup("accountService")
    public AccountService accountService() {
        return null;
    }

}
