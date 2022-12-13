package com.jeasy.cxf.service;

import com.jeasy.cxf.entity.User;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@WebService
public interface HelloService {

    /**
     * sayHi
     *
     * @param text
     * @return
     */
    String sayHi(@WebParam(name = "text") String text);

    /**
     * sayToUser
     *
     * @param user
     * @return
     */
    String sayToUser(@WebParam(name = "user") User user);

    /**
     * getCurrentUser
     *
     * @return
     */
    User getCurrentUser();
}
