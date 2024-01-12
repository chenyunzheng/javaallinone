package com.chris.allinone;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class AllinoneApplicationTests {

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Test
	void contextLoads() {
		String hello = stringRedisTemplate.opsForValue().get("hello");
		System.out.println(hello);
	}

}
