package com.jeasy.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * 发布消息测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/redis/spring-redis.xml")
public class RedisPubTest {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> template;

    /**
     * 发布消息
     */
    @Test
    public void pub() {
        template.convertAndSend("java", "java我发布的消息!");
    }
}
