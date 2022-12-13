package com.jeasy.scheduler.task;

/**
 * Spring Quartz task 定时任务测试，适用于单系统
 * 注意: 适用于集群
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class QuartzTask {

    public void cronTest() {
        System.out.println("[QuartzTask]cron task execute" + System.currentTimeMillis());
    }
}
