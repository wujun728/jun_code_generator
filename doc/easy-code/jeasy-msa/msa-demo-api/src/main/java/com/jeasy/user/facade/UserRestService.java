package com.jeasy.user.facade;

import com.jeasy.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;
import javax.ws.rs.core.Context;

/**
 * This interface acts as some kind of service broker for the original UserService
 * <p/>
 * Here we want to simulate the twitter/weibo rest api, e.g.
 * <p/>
 * http://localhost:8888/user/1.json
 * http://localhost:8888/user/1.xml
 *
 * @author TaoBangren
 * @version 1.0
 * @since 2017/5/17 上午9:26
 */
public interface UserRestService {

    /**
     * 根据id，获取用户
     * @param id
     * @param request
     * @return
     */
    User getUser(@Min(value = 1L, message = "User ID must be greater than 1") Long id, @Context HttpServletRequest request);

    /**
     * 注册用户
     * @param user
     * @return
     */
    RegistrationResult registerUser(User user);
}
