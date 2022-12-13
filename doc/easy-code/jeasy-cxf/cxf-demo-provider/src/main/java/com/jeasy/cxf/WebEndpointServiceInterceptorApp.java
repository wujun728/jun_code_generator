package com.jeasy.cxf;

import com.jeasy.cxf.interceptor.AuthInterceptor;
import com.jeasy.cxf.service.HelloService;
import com.jeasy.cxf.service.impl.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxws.EndpointImpl;

import javax.xml.ws.Endpoint;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class WebEndpointServiceInterceptorApp {

    public static void main(String[] args) {
        log.info("Endpoint Web Service start。。。");

        HelloService implementor = new HelloServiceImpl();
        String address = "http://localhost:9001/helloWorld";

        EndpointImpl endpoint = (EndpointImpl) Endpoint.publish(address, implementor);
        endpoint.getInInterceptors().add(new AuthInterceptor());

        log.info("Endpoint Web Service started。。。");
    }
}
