package com.chris.allinone.spring.vmybatisapp.dao;

import org.apache.ibatis.annotations.Select;

/**
 * @author chrischen
 */
public interface OrderMapper {

    @Select("select 'order'")
    String selectById();
}
