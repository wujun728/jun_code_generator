package com.jeasy.rabbitmq.producer;

import com.jeasy.common.str.StrKit;
import com.jeasy.common.thread.ThreadPoolKit;
import com.jeasy.rabbitmq.entity.UserEntity;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Service
public class RabbitmqProducerTest {

    public static final int NUM_100 = 100;
    public static final int NUM_10 = 10;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @SuppressWarnings("resource")
    public static void main(String[] args) throws InterruptedException {
        RabbitmqProducerTest rabbitmqProducer = new ClassPathXmlApplicationContext("rabbitmq-demo-producer.xml").getBean(RabbitmqProducerTest.class);
        rabbitmqProducer.sendDirectMsg();
        rabbitmqProducer.sendTopicMsg();
    }

    /**
     * 发送消息
     */
    public void sendDirectMsg() {
        ThreadPoolKit.execute(new Runnable() {
            @Override
            public void run() {
                //发送失败时，将会调用这个回调方法
                rabbitTemplate.setConfirmCallback(new ConfirmCallback() {

                    @Override
                    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                        System.out.println("My Confirm Callback，ack:" + ack + " , cause:" + cause);
                    }

                });
                System.out.println("开始发送.....");
                for (int i = 0; i < NUM_100; i++) {
                    try {
                        rabbitTemplate.convertAndSend("exchange.direct", "queue.test.1", UserEntity.newInstance());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("发送成功!!");
            }
        });
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     */
    public void sendFanoutMsg() {
        System.out.println("开始发送.....");
        for (int i = 0; i < NUM_100; i++) {
            rabbitTemplate.convertAndSend("exchange.fanout", StrKit.S_EMPTY, UserEntity.newInstance());
        }
        System.out.println("发送成功!!");
    }

    /**
     */
    public void sendTopicMsg() {
        System.out.println("开始发送.....");
        // for (int i = 0; i < 10; i++) {
        // rabbitTemplate.convertAndSend("exchange.topic", "a.1",
        // UserEntity.newInstance());
        // }
        for (int i = 0; i < NUM_10; i++) {
            rabbitTemplate.convertAndSend("exchange.topic", "queue.test.2", UserEntity.newInstance());
        }
        // for (int i = 0; i < 10; i++) {
        // rabbitTemplate.convertAndSend("exchange.topic", "queue.test.3",
        // UserEntity.newInstance());
        // }
        System.out.println("发送成功!!");
    }
}
