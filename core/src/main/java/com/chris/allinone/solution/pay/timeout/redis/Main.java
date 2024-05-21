package com.chris.allinone.solution.pay.timeout.redis;

import com.chris.allinone.SpringAppContextHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

public class Main {

    public static void main(String[] args) {
        ApplicationContext appContext = SpringAppContextHelper.getAppContext();
        StringRedisTemplate redisTemplate = appContext.getBean(StringRedisTemplate.class);
        produceRecords(redisTemplate);
        consumeRecords(redisTemplate);
    }

    private static void consumeRecords(StringRedisTemplate redisTemplate) {

    }

    private static void produceRecords(StringRedisTemplate redisTemplate) {
        String key = "OrderId";
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
//        zSetOperations.add()
    }
}
