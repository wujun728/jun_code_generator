package com.jeasy.jdk;

import com.jeasy.common.json.JsonKit;
import lombok.extern.slf4j.Slf4j;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class WebJdkServiceClient {
    public static void main(String[] args) {
        HelloServiceService service = new HelloServiceService();
        HelloService helloService = service.getHelloServicePort();

        System.out.println(helloService.sayHi("world"));

        User user = new User();
        user.setName("Taomk");
        System.out.println(helloService.sayToUser(user));

        System.out.println("I am " + JsonKit.toJson(helloService.getCurrentUser()));
    }
}
