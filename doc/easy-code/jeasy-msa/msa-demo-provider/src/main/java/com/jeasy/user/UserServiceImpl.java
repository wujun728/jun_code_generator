package com.jeasy.user;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
@Slf4j
public class UserServiceImpl implements UserService {

    private final AtomicLong idGen = new AtomicLong();

    @Override
    public User getUser(Long id) {
        User user = new User();
        user.setId(id);
        user.setName("username" + id);
        return user;
    }


    @Override
    public Long registerUser(User user) {
        return idGen.incrementAndGet();
    }
}
