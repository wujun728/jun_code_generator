package com.jeasy.redis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * redis 发送测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/redis/spring-redis.xml")
public class RedisTest {

    @Resource(name = "redisTemplate")
    private RedisTemplate<String, String> template;

    private String stringKey = "stringKey";
    private String listKey = "listKey";
//    private String hashKey = "hashKey";
//    private String setKey = "setKey";
//    private String sortSetKey = "sortSetKey";

    /**
     */
    @Test
    public void sendString() {
        try {
            template.boundValueOps(stringKey).set("hello", 10, TimeUnit.SECONDS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     */
    @Test
    public void getString() {
        try {
            String temp = template.boundValueOps(stringKey).get();
            System.out.println("key:" + stringKey + "，value:" + temp);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void pushList() {
        try {
            template.boundListOps(listKey).leftPush("list1");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getList() {
        try {
            System.out.println(template.boundListOps(listKey).leftPop());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
