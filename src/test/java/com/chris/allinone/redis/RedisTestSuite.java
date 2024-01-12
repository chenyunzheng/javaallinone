package com.chris.allinone.redis;

import com.chris.allinone.TestX;
import lombok.extern.slf4j.Slf4j;
import org.redisson.RedissonReadWriteLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
@Slf4j
class RedisTestSuite {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /***
     * 1. 针对缓存：缓存+过期时间，能解决80%问题；针对数据库：写数据库 + 删除缓存（避免双写一致性）
     * 2. 热点key缓存重建：mutex key + setnx + 递归
     * 3. 缓存与数据库双写一致性
     * db和redis的数据同步问题，参见：https://www.cnblogs.com/crazymakercircle/p/14853622.html
     */

    @TestX("热点key重建优化")
    void testHotKeyRebuild() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);
        String hotKey = "hello";
        stringRedisTemplate.delete(hotKey);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0 ; i < 10000; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    String value = getHotKey(hotKey);
                    log.info(Thread.currentThread().getName() + ", hot value = " + value);
                    count.incrementAndGet();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "线程-" + i).start();
        }
        Thread.sleep(500);
        //并发走起
        countDownLatch.countDown();
        Thread.sleep(10000);
        log.info("线程完成个数：" + count.get());
    }

    String getHotKey(String key) {
        String value = stringRedisTemplate.opsForValue().get(key);
        if (value == null) {
            String mutexKey = "mutex:key:" + key;
            if (stringRedisTemplate.opsForValue().setIfAbsent(mutexKey, "1", 200, TimeUnit.MILLISECONDS)) {
                //simulate db call
                try {
                    log.info("线程：" + Thread.currentThread().getName() + " 获取Mutex锁");
                    Thread.sleep(300);
                    value = "hot key rebuild";
                    stringRedisTemplate.opsForValue().set(key, value, 5, TimeUnit.MINUTES);
                    stringRedisTemplate.delete(mutexKey);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    //需要调整，或者用自旋方式改进
                    Thread.sleep(50);
                    //System.out.println(Thread.currentThread().getName() + "等待下一轮getHotKey()");
                    value = getHotKey(key);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    @TestX(value = "最佳实践：更新数据库成功后删除缓存", desc = "数据库写+删掉缓存基本能解决对一致性要求不高的场景")
    void testDBUpdateRedisDel() {
        String key = "testDBUpdateRedisDel";
        //最佳实践：更新数据库后删除缓存
        try {
            //模拟数据库更新（带事务）
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //db更新成功后，删除缓存
        stringRedisTemplate.delete(key);
        String value = getHotKey(key);
        log.info("db更新后数据value：" + value);
    }

    @TestX(value = "缓存与数据库双写一致性",desc = "双写若不是原子性，在大并发下就存在数据不一致问题")
    void testRedisDBBothWrite() {
        //最佳实践：
        //写数据库 + 删除缓存
        //读数据库 + 设置缓存、过期时间

        //对数据一致性要求高的场景，使用分布式锁（类似synchronized）
        String key = "testRedisDBBothWrite";
//        RReadWriteLock lock =

    }
}
