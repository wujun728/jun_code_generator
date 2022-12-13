package com.jeasy.user.facade;

import com.alibaba.dubbo.rpc.RpcContext;
import com.jeasy.user.User;
import com.jeasy.user.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
@Slf4j
public class AnotherUserRestServiceImpl implements AnotherUserRestService {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User getUser(Long id) {
        System.out.println("Client name is " + RpcContext.getContext().getAttachment("clientName"));
        System.out.println("Client impl is " + RpcContext.getContext().getAttachment("clientImpl"));
        return userService.getUser(id);
    }

    @Override
    public RegistrationResult registerUser(User user) {
        Long id = userService.registerUser(user);
        RegistrationResult registrationResult = new RegistrationResult();
        registrationResult.setId(id);
        return registrationResult;
    }
}
