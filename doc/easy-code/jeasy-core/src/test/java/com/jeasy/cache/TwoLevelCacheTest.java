package com.jeasy.cache;

import com.jeasy.cache.service.UserService;
import com.jeasy.common.json.JsonKit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/cache/spring-j2cache-cache.xml")
public class TwoLevelCacheTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserById() {
        try {
            for (int i = 0; i < 10; i++) {
                String user = userService.getUserNameByIdWithFastJson(1l);
                System.out.println(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            for (int i = 0; i < 10; i++) {
                UserService.User user = userService.getUserById(1l);
                System.out.println(JsonKit.toJson(user));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
