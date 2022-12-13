package com.jeasy.scheduler.config;

import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import com.jeasy.scheduler.ZkScheduleManager;
import com.jeasy.scheduler.quartz.MethodInvokingJobDetailFactoryBean;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
@Data
public class ScheduleAutoConfiguration {

    @Autowired
    private ScheduleConfig uncodeScheduleConfig;

    public ZkScheduleManager commonMapper() {
        ZkScheduleManager zkScheduleManager = new ZkScheduleManager();
        zkScheduleManager.setZkConfig(uncodeScheduleConfig.getConfig());
        log.info("=====>ZKScheduleManager inited..");
        return zkScheduleManager;
    }

    public SchedulerFactoryBean quartzSchedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        if (Func.isNotEmpty(uncodeScheduleConfig.getQuartzBean())
            && Func.isNotEmpty(uncodeScheduleConfig.getQuartzMethod())
            && Func.isNotEmpty(uncodeScheduleConfig.getQuartzCronExpression())) {
            int len = uncodeScheduleConfig.getQuartzBean().size();
            List<Trigger> triggers = new ArrayList<Trigger>();
            for (int i = 0; i < len; i++) {
                String name = uncodeScheduleConfig.getQuartzBean().get(i);
                String method = uncodeScheduleConfig.getQuartzMethod().get(i);
                String cronExpression = uncodeScheduleConfig.getQuartzCronExpression().get(i);
                if (StrKit.isNotBlank(name) && StrKit.isNotBlank(method) && StrKit.isNotBlank(cronExpression)) {
                    MethodInvokingJobDetailFactoryBean methodInvokingJob = new MethodInvokingJobDetailFactoryBean();
                    methodInvokingJob.setTargetBeanName(name);
                    methodInvokingJob.setTargetMethod(method);
                    CronTriggerFactoryBean cronTrigger = new CronTriggerFactoryBean();
                    cronTrigger.setJobDetail(methodInvokingJob.getObject());
                    triggers.add(cronTrigger.getObject());
                }
            }
            if (Func.isNotEmpty(triggers)) {
                schedulerFactoryBean.setTriggers(triggers.toArray(new Trigger[triggers.size()]));
            }
        }
        log.info("=====>QuartzSchedulerFactoryBean inited..");
        return schedulerFactoryBean;
    }
}
