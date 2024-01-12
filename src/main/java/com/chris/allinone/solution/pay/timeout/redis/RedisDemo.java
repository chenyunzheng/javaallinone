package com.chris.allinone.solution.pay.timeout.redis;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDemo implements ApplicationRunner {

    private static final int OFFSET = 8 * 60 * 60;
    private static final String ZSET_KEY = "Merchant:OrderIdSet";
    private static final int TIMEOUT_SECONDS = 30;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>>>> Redis ZSet 实现订单超时删除功能");
        produceRecords();
        consumeRecords();
    }

    private void consumeRecords() {
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        Thread thread = new Thread(() -> {
            while (true) {
                LocalDateTime now = LocalDateTime.now();
                long timeoutEnd = now.minusSeconds(TIMEOUT_SECONDS).toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli();
                Set<String> range = zSetOperations.rangeByScore(ZSET_KEY, 0, timeoutEnd);
                if (Objects.nonNull(range) && !range.isEmpty()) {
                    //也可批量数据库删除
                    for (String orderId : range) {
                        System.out.println(Thread.currentThread().getName() + " Go to database to delete OrderId: " + orderId);
                    }
                    //删除Redis缓存
                    zSetOperations.removeRangeByScore(ZSET_KEY, 0, timeoutEnd);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void produceRecords() {
        try {
            TimeUnit.SECONDS.sleep(2);
            System.out.println("模拟数据库订单存入完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //订单id存入Redis（此处牵出db和redis的数据同步问题，参见：https://www.cnblogs.com/crazymakercircle/p/14853622.html）
        LocalDateTime now = LocalDateTime.now();
        ZSetOperations<String, String> zSetOperations = stringRedisTemplate.opsForZSet();
        String valuePrefix = "order:id:00";
        int count = 5;
        long milliSecs = now.minusSeconds(10).toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli();
        for (int i = 0; i < count; i++) {
            zSetOperations.add(ZSET_KEY, valuePrefix + i, milliSecs);
        }
        //第二批orders
        count = 10;
        valuePrefix = "order:id:99";
        milliSecs = now.minusSeconds(5).toInstant(ZoneOffset.ofTotalSeconds(OFFSET)).toEpochMilli();
        for (int i = 0; i < count; i++) {
            zSetOperations.add(ZSET_KEY, valuePrefix + i, milliSecs);
        }
        System.out.println("订单id存入完毕");
    }
}
