package com.jeasy.jdk.service;

import com.jeasy.jdk.service.impl.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.xml.ws.Endpoint;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class WebJdkServiceApp {

    public static void main(String[] args) {
        String address = "http://localhost:9002/helloWorld";
        HelloService helloService = new HelloServiceImpl();
        Endpoint.publish(address, helloService);
        System.out.println("jdk ws is published");
    }
}
