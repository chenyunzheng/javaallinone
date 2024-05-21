package com.chris.allinone.redis;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redis")
public class DemoController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/key/{keyValue}")
    public String getOp(@PathVariable("keyValue") String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @PostMapping("/key/{keyValue}")
    public String getOp(@PathVariable("keyValue") String key, @RequestParam("value") String value) {
        stringRedisTemplate.opsForValue().set(key, value, 3, TimeUnit.MINUTES);
        return "ACK";
    }
}
