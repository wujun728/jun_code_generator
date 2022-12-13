package com.jeasy.cxf.client;

import com.jeasy.cxf.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class WebServiceClient {
    public static void main(String[] args) {
        JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
        svr.setServiceClass(HelloService.class);
        svr.setAddress("http://localhost:9000/helloWorld");
        HelloService hw = (HelloService) svr.create();

        log.info(hw.sayHi("刘晓。。。。。。。。。"));
    }
}
