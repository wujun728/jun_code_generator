package com.jeasy.scheduler;

import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import com.jeasy.scheduler.core.ScheduledMethodRunnable;
import com.jeasy.scheduler.core.TaskDefine;
import com.jeasy.scheduler.util.ScheduleUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.aop.support.AopUtils;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public class DynamicTaskManager {

    private static final Map<String, ScheduledFuture<?>> SCHEDULE_FUTURES = new ConcurrentHashMap<String, ScheduledFuture<?>>();
    private static final Map<String, TaskDefine> TASKS = new ConcurrentHashMap<String, TaskDefine>();

    /**
     * 启动定时任务
     *
     * @param taskDefine
     * @param currentTime
     */
    public static void scheduleTask(final TaskDefine taskDefine, final Date currentTime) {
        boolean newTask = true;
        if (SCHEDULE_FUTURES.containsKey(taskDefine.stringKey())) {
            if (taskDefine.equals(TASKS.get(taskDefine.stringKey()))) {
                newTask = false;
            } else {
                SCHEDULE_FUTURES.get(taskDefine.stringKey()).cancel(true);
                SCHEDULE_FUTURES.remove(taskDefine.stringKey());
            }
        }
        if (newTask) {
            TASKS.put(taskDefine.stringKey(), taskDefine);
            scheduleTask(taskDefine.getTargetBean(), taskDefine.getTargetMethod(), taskDefine.getCronExpression(), taskDefine.getStartTime(), taskDefine.getPeriod(), taskDefine.getParams(), taskDefine.getExtKeySuffix());
        }
    }

    public static void clearLocalTask(final List<String> existsTaskName) {
        for (Map.Entry<String, ScheduledFuture<?>> entry : SCHEDULE_FUTURES.entrySet()) {
            if (!existsTaskName.contains(entry.getKey())) {
                entry.getValue().cancel(true);
                SCHEDULE_FUTURES.remove(entry.getKey());
                TASKS.remove(entry.getKey());
            }
        }
    }

    /**
     * 启动定时任务
     * 支持：
     * 1 cron时间表达式，立即执行
     * 2 startTime + period,指定时间，定时进行
     * 3 period，定时进行，立即开始
     * 4 startTime，指定时间执行
     *
     * @param targetBean
     * @param targetMethod
     * @param cronExpression
     * @param startTime
     * @param period
     */
    public static void scheduleTask(final String targetBean, final String targetMethod, final String cronExpression, final Date startTime, final long period, final String params, final String extKeySuffix) {
        String scheduleKey = ScheduleUtil.buildScheduleKey(targetBean, targetMethod, extKeySuffix);
        try {
            if (!SCHEDULE_FUTURES.containsKey(scheduleKey)) {
                ScheduledFuture<?> scheduledFuture = null;
                ScheduledMethodRunnable scheduledMethodRunnable = buildScheduledRunnable(targetBean, targetMethod, params, extKeySuffix);
                if (Func.isNotEmpty(scheduledMethodRunnable)) {
                    if (StrKit.isNotEmpty(cronExpression)) {
                        Trigger trigger = new CronTrigger(cronExpression);
                        scheduledFuture = ConsoleManager.getScheduleManager().schedule(scheduledMethodRunnable, trigger);
                    } else if (Func.isNotEmpty(startTime)) {
                        if (period > 0) {
                            scheduledFuture = ConsoleManager.getScheduleManager().scheduleAtFixedRate(scheduledMethodRunnable, startTime, period);
                        } else {
                            scheduledFuture = ConsoleManager.getScheduleManager().schedule(scheduledMethodRunnable, startTime);
                        }
                    } else if (period > 0) {
                        scheduledFuture = ConsoleManager.getScheduleManager().scheduleAtFixedRate(scheduledMethodRunnable, period);
                    }
                    if (Func.isNotEmpty(scheduledFuture)) {
                        SCHEDULE_FUTURES.put(scheduleKey, scheduledFuture);
                        log.debug("Building new schedule task, target bean " + targetBean + " target method " + targetMethod + ".");
                    }
                } else {
                    ConsoleManager.getScheduleManager().getScheduleDataManager().saveRunningInfo(scheduleKey, ConsoleManager.getScheduleManager().getScheduleServerUUid(), "bean not exists");
                    log.debug("Bean name is not exists.");
                }
            }
        } catch (Exception e) {
            log.error("scheduleTask error ", e);
        }
    }

    /**
     * 封装任务对象
     *
     * @param targetBean
     * @param targetMethod
     * @return
     */
    private static ScheduledMethodRunnable buildScheduledRunnable(final String targetBean, final String targetMethod, final String params, final String extKeySuffix) {
        Object bean;
        ScheduledMethodRunnable scheduledMethodRunnable = null;
        try {
            bean = ZkScheduleManager.getApplicationContext().getBean(targetBean);
            scheduledMethodRunnable = buildScheduledRunnable(bean, targetMethod, params, extKeySuffix);
        } catch (Exception e) {
            String name = ScheduleUtil.buildScheduleKey(targetBean, targetMethod, extKeySuffix);
            try {
                ConsoleManager.getScheduleManager().getScheduleDataManager().saveRunningInfo(name, ConsoleManager.getScheduleManager().getScheduleServerUUid(), "method is null");
            } catch (Exception e1) {
                log.debug(e.getLocalizedMessage(), e);
            }
            log.debug(e.getLocalizedMessage(), e);
        }
        return scheduledMethodRunnable;
    }

    private static ScheduledMethodRunnable buildScheduledRunnable(final Object bean, final String targetMethod, final String params, final String extKeySuffix) throws Exception {

        Assert.notNull(bean, "target object must not be null");
        Assert.hasLength(targetMethod, "Method name must not be empty");

        Method method;
        ScheduledMethodRunnable scheduledMethodRunnable;

        Class<?> clazz;
        if (AopUtils.isAopProxy(bean)) {
            clazz = AopProxyUtils.ultimateTargetClass(bean);
        } else {
            clazz = bean.getClass();
        }
        if (Func.isNotEmpty(params)) {
            method = ReflectionUtils.findMethod(clazz, targetMethod, String.class);
        } else {
            method = ReflectionUtils.findMethod(clazz, targetMethod);
        }
        Assert.notNull(method, "can not find method named " + targetMethod);
        scheduledMethodRunnable = new ScheduledMethodRunnable(bean, method, params, extKeySuffix);
        return scheduledMethodRunnable;
    }
}
