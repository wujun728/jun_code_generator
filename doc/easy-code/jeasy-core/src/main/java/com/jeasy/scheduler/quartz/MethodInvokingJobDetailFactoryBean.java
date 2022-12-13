package com.jeasy.scheduler.quartz;

import com.jeasy.common.spring.SpringContextHolder;
import com.jeasy.scheduler.ConsoleManager;
import com.jeasy.scheduler.core.TaskDefine;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.*;
import org.springframework.beans.support.ArgumentConvertingMethodInvoker;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.MethodInvoker;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class MethodInvokingJobDetailFactoryBean extends ArgumentConvertingMethodInvoker implements FactoryBean<JobDetail>, BeanNameAware, BeanClassLoaderAware, BeanFactoryAware, InitializingBean {

    private static Class<?> jobDetailImplClass;

    private static Method setResultMethod;

    static {
        try {
            jobDetailImplClass = Class.forName("org.quartz.impl.JobDetailImpl");
        } catch (ClassNotFoundException ex) {
            jobDetailImplClass = null;
        }
        try {
            Class<?> jobExecutionContextClass = QuartzJobBean.class.getClassLoader().loadClass("org.quartz.JobExecutionContext");
            setResultMethod = jobExecutionContextClass.getMethod("setResult", Object.class);
        } catch (Exception ex) {
            throw new IllegalStateException("Incompatible Quartz API: " + ex);
        }
    }


    private String name;

    private String group = Scheduler.DEFAULT_GROUP;

    private boolean concurrent = true;

    private String targetBeanName;

    private String[] jobListenerNames;

    private String beanName;

    private ClassLoader beanClassLoader = ClassUtils.getDefaultClassLoader();

    private BeanFactory beanFactory;

    private JobDetail jobDetail;

    @Override
    protected Class<?> resolveClassName(final String className) throws ClassNotFoundException {
        return ClassUtils.forName(className, this.beanClassLoader);
    }


    @Override
    public void afterPropertiesSet() throws ClassNotFoundException, NoSuchMethodException {
        prepare();

        // Use specific name if given, else fall back to bean name.
        String name = this.name != null ? this.name : this.beanName;

        // Consider the concurrent flag to choose between stateful and stateless job.
        Class<?> jobClass = (this.concurrent ? MethodInvokingJob.class : StatefulMethodInvokingJob.class);

        // Build JobDetail instance.
        if (jobDetailImplClass != null) {
            // Using Quartz 2.0 JobDetailImpl class...
            this.jobDetail = (JobDetail) BeanUtils.instantiate(jobDetailImplClass);
            BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(this.jobDetail);
            bw.setPropertyValue("name", name);
            bw.setPropertyValue("group", this.group);
            bw.setPropertyValue("jobClass", jobClass);
            bw.setPropertyValue("durability", true);
            ((JobDataMap) bw.getPropertyValue("jobDataMap")).put("methodInvoker", this);
        } else {
            // Using Quartz 1.x JobDetail class...
            // this.jobDetail = new JobDetail(name, this.group, jobClass);
            // this.jobDetail.setVolatility(true);
			// this.jobDetail.setDurability(true);
			// this.jobDetail.getJobDataMap().put("methodInvoker", this);
        }

        // Register job listener names.
        if (this.jobListenerNames != null) {
            for (String jobListenerName : this.jobListenerNames) {
                if (jobListenerName != null) {
                    throw new IllegalStateException("Non-global JobListeners not supported on Quartz 2 - " +
                        "manually register a Matcher against the Quartz ListenerManager instead");
                }
                // this.jobDetail.addJobListener(jobListenerName);
            }
        }

        postProcessJobDetail(this.jobDetail);
    }

    /**
     * Callback for post-processing the JobDetail to be exposed by this FactoryBean.
     * <p>The default implementation is empty. Can be overridden in subclasses.
     *
     * @param jobDetail the JobDetail prepared by this FactoryBean
     */
    protected void postProcessJobDetail(final JobDetail jobDetail) {
    }


    /**
     * Overridden to support the {@link #setTargetBeanName "targetBeanName"} feature.
     */
    @Override
    public Class<?> getTargetClass() {
        Class<?> targetClass = super.getTargetClass();
        if (targetClass == null && this.targetBeanName != null) {
            Assert.state(this.beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
            targetClass = this.beanFactory.getType(this.targetBeanName);
        }
        return targetClass;
    }

    /**
     * Overridden to support the {@link #setTargetBeanName "targetBeanName"} feature.
     */
    @Override
    public Object getTargetObject() {
        Object targetObject = super.getTargetObject();
        if (targetObject == null && this.targetBeanName != null) {
            Assert.state(this.beanFactory != null, "BeanFactory must be set when using 'targetBeanName'");
            targetObject = this.beanFactory.getBean(this.targetBeanName);
        }
        return targetObject;
    }

    public String getTargetBeanName() {
        String[] names = SpringContextHolder.getContext().getBeanNamesForType(getTargetClass());
        if (null != names) {
            return names[0];
        }
        return null;
    }


    @Override
    public JobDetail getObject() {
        return this.jobDetail;
    }

    @Override
    public Class<? extends JobDetail> getObjectType() {
        return (this.jobDetail != null ? this.jobDetail.getClass() : JobDetail.class);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }


    /**
     * Quartz Job implementation that invokes a specified method.
     * Automatically applied by MethodInvokingJobDetailFactoryBean.
     */
    @Slf4j
    public static class MethodInvokingJob extends QuartzJobBean {

        private MethodInvoker methodInvoker;

        /**
         * Set the MethodInvoker to use.
         */
        public void setMethodInvoker(final MethodInvoker methodInvoker) {
            this.methodInvoker = methodInvoker;
        }

        /**
         * Invoke the method via the MethodInvoker.
         */
        @Override
        protected void executeInternal(final JobExecutionContext context) throws JobExecutionException {
            TaskDefine taskDefine = new TaskDefine();
            MethodInvokingJob methodInvokingJob = (MethodInvokingJob) context.getJobInstance();
            MethodInvokingJobDetailFactoryBean methodInvokingJobDetailFactoryBean = (MethodInvokingJobDetailFactoryBean) methodInvokingJob.methodInvoker;
            taskDefine.setTargetBean(methodInvokingJobDetailFactoryBean.getTargetBeanName());
            taskDefine.setTargetMethod(this.methodInvoker.getTargetMethod());
            taskDefine.setType(TaskDefine.TYPE_QUARTZ_TASK);
            if (context.getTrigger() instanceof org.quartz.CronTrigger) {
                CronTriggerImpl cronTriggerImpl = (org.quartz.impl.triggers.CronTriggerImpl) context.getTrigger();
                if (null != cronTriggerImpl.getCronExpression()) {
                    taskDefine.setCronExpression(cronTriggerImpl.getCronExpression());
                }
                if (null != cronTriggerImpl.getStartTime()) {
                    taskDefine.setStartTime(cronTriggerImpl.getStartTime());
                }
                if (cronTriggerImpl.getPriority() > 0) {
                    taskDefine.setPeriod(cronTriggerImpl.getPriority());
                }
            }
            String name = taskDefine.stringKey();
            boolean isOwner = false;
            boolean isRunning = true;
            try {
                boolean isExists;
                if (ConsoleManager.getScheduleManager().getZkManager().checkZookeeperState()) {
                    isExists = ConsoleManager.isExistsTask(taskDefine);
                    if (!isExists) {
                        ConsoleManager.addScheduleTask(taskDefine);
                    }
                    isOwner = ConsoleManager.getScheduleManager().getScheduleDataManager().isOwner(name, ConsoleManager.getScheduleManager().getScheduleServerUUid());
                    ConsoleManager.getScheduleManager().getIsOwnerMap().put(name, isOwner);
                } else {
                    // 如果zk不可用，使用历史数据
                    if (null != ConsoleManager.getScheduleManager().getIsOwnerMap()) {
                        isOwner = ConsoleManager.getScheduleManager().getIsOwnerMap().get(name);
                    }
                }
                isRunning = ConsoleManager.getScheduleManager().getScheduleDataManager().isRunning(name);
            } catch (Exception e) {
                log.error("Check task owner error.", e);
            }
            if (isOwner && isRunning) {
                String msg = null;
                try {
                    ReflectionUtils.invokeMethod(setResultMethod, context, this.methodInvoker.invoke());
                    log.info("Cron job has been executed.");
                } catch (InvocationTargetException | IllegalAccessException e) {
                    msg = e.getLocalizedMessage();
                }
                try {
                    ConsoleManager.getScheduleManager().getScheduleDataManager().saveRunningInfo(name, ConsoleManager.getScheduleManager().getScheduleServerUUid(), msg);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    /**
     * Extension of the MethodInvokingJob, implementing the StatefulJob interface.
     * Quartz checks whether or not jobs are stateful and if so,
     * won't let jobs interfere with each other.
     */
    public static class StatefulMethodInvokingJob extends MethodInvokingJob implements Job {
        // No implementation, just an addition of the tag interface StatefulJob
        // in order to allow stateful method invoking jobs.
    }
}
