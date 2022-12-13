package com.jeasy.rabbitmq.consumer;

import com.jeasy.common.json.JsonKit;
import com.jeasy.rabbitmq.entity.UserEntity;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Service
public class RabbitmqConsumerTest {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws InterruptedException {
        new ClassPathXmlApplicationContext("rabbitmq-demo-consumer.xml");
        while (true) {
            Thread.sleep(1000);
        }
    }

    /**
     * 接收队列1消息
     *
     * @param o
     */
    public void handTest1Msg(UserEntity o) {
        System.out.println("Test1 msg:" + JsonKit.toJson(o));
    }

    /**
     * 接收队列2消息
     *
     * @param o
     */
    public void handTest2Msg(Object o) {
        System.out.println("Test2 msg:" + JsonKit.toJson(o));
    }

    /**
     * 接收队列3消息
     *
     * @param o
     */
    public void handTest3Msg(Object o) {
        System.out.println("Test3 msg:" + JsonKit.toJson(o));
    }
}
