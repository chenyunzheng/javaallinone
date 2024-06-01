package com.chris.allinone.spring.vmybatisapp.dao;

import org.apache.ibatis.annotations.Select;

/**
 * @author chrischen
 */
public interface AccountMapper {

    @Select("select 'account'")
    String selectByUserId();
}
