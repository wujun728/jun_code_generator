package com.jeasy.cxf;

import com.jeasy.cxf.interceptor.AuthInterceptor;
import com.jeasy.cxf.service.HelloService;
import com.jeasy.cxf.service.impl.HelloServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class WebJaxWsServiceInterceptorApp {

    public static void main(String[] args) {
        JaxWsServerFactoryBean bean = new JaxWsServerFactoryBean();

        // 服务的地址
        bean.setAddress("http://localhost:9000/helloWorld");

        // 提供服务的类的类型
        bean.setServiceClass(HelloService.class);
        // 提供服务的实例
        bean.setServiceBean(new HelloServiceImpl());
        bean.getInInterceptors().add(new AuthInterceptor());

        bean.create(); //发布服务  publish()
        log.info("JaxWs server ready...");
    }
}
