package com.jeasy.cache;

import com.jeasy.cache.service.UserService;
import com.jeasy.common.json.JsonKit;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/cache/spring-memcached-cache.xml")
public class MemcachedCacheTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserNameByIdWithFastJson() {
        try {
            for (int i = 100; i < 120; i++) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                String name = userService.getUserNameByIdWithFastJson(1l);
                stopWatch.stop();
                System.out.println("耗时：" + stopWatch.getTime() + "  " + name + " " + i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getUserNameByIdWithJdk() {
        try {
            for (int i = 0; i < 20; i++) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                String name = userService.getUserNameByIdWithJdk(Long.valueOf(i));
                stopWatch.stop();
                System.out.println("耗时：" + stopWatch.getTime() + "  " + name + " " + i);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getUserByIdWithFastjson() {
        try {
            for (int i = 100; i < 110; i++) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                UserService.User user = userService.getUserByIdWithFastjson(Long.valueOf(i));
                stopWatch.stop();
                System.out.println("耗时：" + stopWatch.getTime() + "  " + JsonKit.toJson(user));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getUserByIdWithJdk() {
        try {
            for (int i = 50; i < 60; i++) {
                StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                UserService.User user = userService.getUserByIdWithJdk(Long.valueOf(i));
                stopWatch.stop();
                System.out.println("耗时：" + stopWatch.getTime() + "  " + JsonKit.toJson(user));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
