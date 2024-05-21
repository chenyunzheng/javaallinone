package com.andrew.vapp.controller;

import com.andrew.vapp.service.VService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
