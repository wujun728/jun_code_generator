package com.jeasy.scheduler.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * spring scheduler测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:applicationContext-*.xml")
public class TaskTest {

    @Test
    public void task() throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
        }
    }
}
