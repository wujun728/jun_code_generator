package com.jeasy.cxf;

import com.jeasy.common.json.JsonKit;
import com.jeasy.cxf.entity.User;
import com.jeasy.cxf.service.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class WebServiceSpringClient {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("cxf-demo-consumer.xml", "cxf-demo-client.xml");
        HelloService helloWorldEd = (HelloService) ctx.getBean("helloWorld_ed");
        HelloService helloWorldJw = (HelloService) ctx.getBean("helloWorld_jw");
        System.out.println(helloWorldEd.sayHi("liuxiao"));
        System.out.println(helloWorldJw.sayHi("liuxiao"));

        HelloService helloWorld = (HelloService) ctx.getBean("helloWorld");
        System.out.println(helloWorld.sayHi("helloWorld"));

        User user = new User();
        user.setName("Taomk");
        System.out.println(helloWorld.sayToUser(user));
        System.out.println("I am " + JsonKit.toJson(helloWorld.getCurrentUser()));
    }
}
