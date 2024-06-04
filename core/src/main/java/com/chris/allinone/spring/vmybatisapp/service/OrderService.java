package com.chris.allinone.spring.vmybatisapp.service;

import com.chris.allinone.spring.vmybatisapp.dao.AccountMapper;
import com.chris.allinone.spring.vmybatisapp.dao.OrderMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author chrischen
 */
@Service
@EnableTransactionManagement
public class OrderService {

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private AccountMapper accountMapper;

    @Transactional
    public void test() {
        System.out.println(orderMapper.selectById());
        System.out.println(accountMapper.selectByUserId());
    }
}
