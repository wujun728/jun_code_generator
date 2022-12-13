package com.jeasy.cache;

import com.jeasy.cache.service.EhcacheUserService;
import com.jeasy.common.json.JsonKit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * ehcache测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/cache/spring-ehcache.xml")
public class EhcacheCacheTest {

    @Autowired
    private EhcacheUserService userService;

    @Test
    public void getUserById() {
        try {
            for (int i = 0; i < 3; i++) {
                String user = userService.getUserNameByIdWithFastJson(2l);
                System.out.println(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            for (int i = 0; i < 3; i++) {
                int user = userService.getUserAgeByIdWithFastJson(3l);
                System.out.println(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            for (int i = 0; i < 3; i++) {
                Long user = userService.getUserIdByIdWithFastJson(2l);
                System.out.println(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            for (int i = 0; i < 3; i++) {
                Float user = userService.getUserPriceByIdWithFastJson(2l);
                System.out.println(user);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            for (int i = 0; i < 3; i++) {
                List<EhcacheUserService.User> user = userService.getUserByIdList(1l);
                System.out.println(JsonKit.toJson(user));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            for (int i = 0; i < 3; i++) {
                Map<String, EhcacheUserService.User> map = userService.getUserByIdMap(1l);
                System.out.println(JsonKit.toJson(map));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
