package com.jeasy.scheduler;

import com.jeasy.common.Func;
import com.jeasy.scheduler.core.TaskDefine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public class ConsoleManager {

    private static ZkScheduleManager scheduleManager;

    public static ZkScheduleManager getScheduleManager() throws Exception {
        if (Func.isEmpty(ConsoleManager.scheduleManager)) {
            synchronized (ConsoleManager.class) {
                ConsoleManager.scheduleManager = ZkScheduleManager.getApplicationContext().getBean(ZkScheduleManager.class);
            }
        }
        return ConsoleManager.scheduleManager;
    }

    public static void addScheduleTask(final TaskDefine taskDefine) {
        try {
            ConsoleManager.getScheduleManager().getScheduleDataManager().addTask(taskDefine);
        } catch (Exception e) {
            log.error("addScheduleTask error", e);
        }
    }

    public static void delScheduleTask(final TaskDefine taskDefine) {
        try {
            ConsoleManager.scheduleManager.getScheduleDataManager().delTask(taskDefine);
        } catch (Exception e) {
            log.error("delScheduleTask error", e);
        }
    }

    public static void delScheduleTask(final String targetBean, final String targetMethod) {
        try {
            TaskDefine taskDefine = new TaskDefine();
            taskDefine.setTargetBean(targetBean);
            taskDefine.setTargetMethod(targetMethod);
            ConsoleManager.scheduleManager.getScheduleDataManager().delTask(taskDefine);
        } catch (Exception e) {
            log.error("delScheduleTask error", e);
        }
    }

    public static void updateScheduleTask(final TaskDefine taskDefine) {
        try {
            ConsoleManager.scheduleManager.getScheduleDataManager().updateTask(taskDefine);
        } catch (Exception e) {
            log.error("updateScheduleTask error", e);
        }
    }

    public static List<TaskDefine> queryScheduleTask() {
        List<TaskDefine> taskDefines = new ArrayList<>();
        try {
            List<TaskDefine> tasks = ConsoleManager.getScheduleManager().getScheduleDataManager().selectTask();
            taskDefines.addAll(tasks);
        } catch (Exception e) {
            log.error("queryScheduleTask error", e);
        }
        return taskDefines;
    }

    public static boolean isExistsTask(final TaskDefine taskDefine) throws Exception {
        return ConsoleManager.scheduleManager.getScheduleDataManager().isExistsTask(taskDefine);
    }
}
