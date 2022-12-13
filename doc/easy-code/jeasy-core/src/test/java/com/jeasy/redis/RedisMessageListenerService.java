package com.jeasy.redis;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 订阅服务类
 */
public class RedisMessageListenerService {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("redis/spring-redis-pub-sub.xml");
        while (true) { // 这里是一个死循环,目的就是让进程不退出,用于接收发布的消息
            try {
                System.out.println("current time: " + new Date());

                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMessage(Serializable message) {
        // 什么都不做,只输出
        if (message == null) {
            System.out.println("null");
        } else if (message.getClass().isArray()) {
            System.out.println(Arrays.toString((Object[]) message));
        } else if (message instanceof List<?>) {
            System.out.println(message);
        } else if (message instanceof Map<?, ?>) {
            System.out.println(message);
        } else {
            System.out.println(ToStringBuilder.reflectionToString(message));
        }
    }
}
