package org.fall.test;

import org.fall.constant.CrowdConstant;
import org.fall.util.CrowdUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

@SpringBootTest
public class RedisTest {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void testRedis01(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set("fruit", "苹果");
        operations.set("pet", "dog");
        System.out.println("--------------OK--------------");
    }

    @Test
    public void testRedis02(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String fruit = operations.get("apple");
        System.out.println(fruit);
    }

    @Test
    public void testRedis03(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        operations.set(CrowdConstant.REDIS_CODE_PREFIX + "13429239971", "1234",10, TimeUnit.MINUTES);
        System.out.println("--------------OK--------------");
    }
}
