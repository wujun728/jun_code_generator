package com.jeasy.scheduler.core;

import com.jeasy.common.Func;
import com.jeasy.common.object.ObjectKit;
import com.jeasy.common.str.StrKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * 任务定义，提供关键信息给使用者
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public class TaskDefine {

    public static final String TYPE_UNCODE_TASK = "uncode-task";
    public static final String TYPE_SPRING_TASK = "spring-task";
    public static final String TYPE_QUARTZ_TASK = "quartz";

    public static final String STATUS_ERROR = "error";
    public static final String STATUS_STOP = "stop";
    public static final String STATUS_RUNNING = "running";

    /**
     * 目标bean
     */
    private String targetBean;

    /**
     * 目标方法
     */
    private String targetMethod;

    /**
     * cron表达式
     */
    private String cronExpression;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 周期（毫秒）
     */
    private long period;

    /**
     * 参数
     */
    private String params;

    /**
     * 类型
     */
    private String type;

    /**
     * 后台显示参数，当前任务执行节点
     */
    private String currentServer;

    /**
     * 后台显示参数，无业务内含
     */
    private int runTimes;

    /**
     * 后台显示参数，无业务内含
     */
    private long lastRunningTime;

    /**
     * 后台显示参数，无业务内含
     */
    private String status = STATUS_RUNNING;

    /**
     * key的后缀
     */
    private String extKeySuffix;

    public final boolean begin(final Date sysTime) {
        return Func.isNotEmpty(sysTime) && sysTime.after(startTime);
    }

    public final String getTargetMethod4Show() {
        if (StrKit.isNotBlank(extKeySuffix)) {
            return targetMethod + StrKit.S_MIDDLELINE + extKeySuffix;
        }
        return targetMethod;
    }

    public final void setTargetMethod(final String targetMethod) {
        if (StrKit.isNotBlank(targetMethod) && targetMethod.contains(StrKit.S_MIDDLELINE)) {
            String[] vals = targetMethod.split(StrKit.S_MIDDLELINE);
            this.targetMethod = vals[0];
            this.extKeySuffix = vals[1];
        } else {
            this.targetMethod = targetMethod;
        }
    }

    public final String stringKey() {
        String result = null;
        boolean notBlank = StrKit.isNotBlank(getTargetBean()) && StrKit.isNotBlank(getTargetMethod());
        if (notBlank) {
            result = getTargetBean() + StrKit.S_POUND + getTargetMethod();
        }
        if (StrKit.isNotBlank(extKeySuffix)) {
            result += StrKit.S_MIDDLELINE + extKeySuffix;
        }
        return result;
    }

    public final boolean isStop() {
        return STATUS_STOP.equals(this.status);
    }

    public final void setStop() {
        this.status = STATUS_STOP;
    }

    public final void valueOf(final TaskDefine taskDefine) {
        if (StrKit.isNotBlank(taskDefine.getTargetBean())) {
            this.targetBean = taskDefine.getTargetBean();
        }
        if (StrKit.isNotBlank(taskDefine.getTargetMethod())) {
            if (taskDefine.getTargetMethod().contains(StrKit.S_MIDDLELINE)) {
                String[] vals = taskDefine.getTargetMethod().split(StrKit.S_MIDDLELINE);
                this.targetMethod = vals[0];
                this.extKeySuffix = vals[1];
            } else {
                this.targetMethod = taskDefine.getTargetMethod();
            }
        }
        if (StrKit.isNotBlank(taskDefine.getCronExpression())) {
            this.cronExpression = taskDefine.getCronExpression();
        }
        if (startTime != null) {
            this.startTime = taskDefine.getStartTime();
        }
        if (taskDefine.getPeriod() > 0) {
            this.period = taskDefine.getPeriod();
        }
        if (StrKit.isNotBlank(taskDefine.getParams())) {
            this.params = taskDefine.getParams();
        }
        if (StrKit.isNotBlank(taskDefine.getType())) {
            this.type = taskDefine.getType();
        }
        if (StrKit.isNotBlank(taskDefine.getStatus())) {
            this.status = taskDefine.getStatus();
        }
        if (StrKit.isNotBlank(taskDefine.getExtKeySuffix())) {
            this.extKeySuffix = taskDefine.getExtKeySuffix();
        }
    }


    @Override
    public final int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((targetBean == null) ? 0 : targetBean.hashCode());
        result = prime * result + ((targetMethod == null) ? 0 : targetMethod.hashCode());
        result = prime * result + ((cronExpression == null) ? 0 : cronExpression.hashCode());
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + (int) period;
        result = prime * result + ((params == null) ? 0 : params.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + ((extKeySuffix == null) ? 0 : extKeySuffix.hashCode());
        return result;
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj == null || !(obj instanceof TaskDefine)) {
            return false;
        }
        TaskDefine ou = (TaskDefine) obj;
        if (!ObjectKit.equals(this.targetBean, ou.targetBean)) {
            return false;
        }
        if (!ObjectKit.equals(this.targetMethod, ou.targetMethod)) {
            return false;
        }
        if (!ObjectKit.equals(this.cronExpression, ou.cronExpression)) {
            return false;
        }
        if (!ObjectKit.equals(this.startTime, ou.startTime)) {
            return false;
        }
        if (!ObjectKit.equals(this.params, ou.params)) {
            return false;
        }
        if (!ObjectKit.equals(this.type, ou.type)) {
            return false;
        }
        return ObjectKit.equals(this.extKeySuffix, ou.extKeySuffix) && this.period == ou.period;
    }
}
