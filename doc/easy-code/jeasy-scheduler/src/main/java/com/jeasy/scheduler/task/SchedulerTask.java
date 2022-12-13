package com.jeasy.scheduler.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Spring scheduler 定时任务测试，适用于单系统
 * 注意: 适用于集群
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Component
public class SchedulerTask {

    @Scheduled(cron = "0/3 * * * * ?")
    public void cronTest() {
        System.out.println("[SchedulerTask]cron task execute" + System.currentTimeMillis());
    }
}
