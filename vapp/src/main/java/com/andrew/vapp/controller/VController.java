package com.andrew.vapp.controller;

import com.chris.allinone.spring.springboot.vapp.service.VService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chrischen
 */
@RestController
public class VController {

    @Resource
    public VService vService;

    @RequestMapping("/hello")
    public String hello() {
        return vService.hello();
    }
}
