package com.jeasy.user;

/**
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public interface UserService {

    /**
     * 根据id, 获取用户
     *
     * @param id
     * @return
     */
    User getUser(Long id);

    /**
     * 注册用户
     *
     * @param user
     * @return
     */
    Long registerUser(User user);
}
