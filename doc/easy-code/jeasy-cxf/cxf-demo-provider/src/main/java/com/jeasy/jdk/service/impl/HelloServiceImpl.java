package com.jeasy.jdk.service.impl;

import com.jeasy.common.json.JsonKit;
import com.jeasy.cxf.entity.User;
import com.jeasy.jdk.service.HelloService;

import javax.jws.WebService;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@WebService(serviceName = "HelloService", portName = "HelloServicePort", endpointInterface = "com.jeasy.jdk.service.HelloService")
public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHi(String text) {
        return "Hello, " + text + " in shark service";
    }

    @Override
    public String sayToUser(User user) {
        return "Hello, " + JsonKit.toJson(user) + " in shark service";
    }

    @Override
    public User getCurrentUser() {
        User user = new User();
        user.setName("Taomk");
        return user;
    }
}
