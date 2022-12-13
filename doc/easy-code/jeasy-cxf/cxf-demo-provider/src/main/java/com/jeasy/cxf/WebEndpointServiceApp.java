package com.jeasy.cxf;

import com.jeasy.cxf.service.HelloService;
import com.jeasy.cxf.service.impl.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.xml.ws.Endpoint;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class WebEndpointServiceApp {

    public static void main(String[] args) {
        log.info("Endpoint Web Service start。。。");

        HelloService implementor = new HelloServiceImpl();
        String address = "http://localhost:9000/helloWorld";
        Endpoint.publish(address, implementor);

        log.info("Endpoint Web Service started。。。");
    }
}
