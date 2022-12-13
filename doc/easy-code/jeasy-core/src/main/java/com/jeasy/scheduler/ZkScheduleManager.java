package com.jeasy.scheduler;

import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import com.jeasy.scheduler.core.IScheduleDataManager;
import com.jeasy.scheduler.core.ScheduleServer;
import com.jeasy.scheduler.core.ScheduledMethodRunnable;
import com.jeasy.scheduler.core.TaskDefine;
import com.jeasy.scheduler.zk.ScheduleDataManager4Zk;
import com.jeasy.scheduler.zk.ZkManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 调度器核心管理
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
public class ZkScheduleManager extends ThreadPoolTaskScheduler implements ApplicationContextAware {

    private static final long serialVersionUID = 1L;
    private static final int NUM_1000 = 1000;

    private static ApplicationContext applicationContext;

    /**
     * 是否启动调度管理，如果只是做系统管理，应该设置为false
     */
    public boolean start = true;

    protected ZkManager zkManager;

    /**
     * 当前调度服务的信息
     */
    protected ScheduleServer currentScheduleServer;

    private final CountDownLatch downLatch = new CountDownLatch(1);

    private Map<String, String> zkConfig;

    private IScheduleDataManager scheduleDataManager;

    /**
     * 心跳间隔
     */
    private int timerInterval = NUM_1000;

    private ScheduledExecutorService hearBeatScheduler = Executors.newSingleThreadScheduledExecutor();

    /**
     * 是否注册成功
     */
    private boolean isScheduleServerRegister = true;

    private Map<String, Boolean> isOwnerMap = new ConcurrentHashMap<String, Boolean>();

    private Lock initLock = new ReentrantLock();
    private boolean isStopSchedule;
    private Lock registerLock = new ReentrantLock();

    private volatile String errorMessage = "No config Zookeeper connect information";
    private InitialThread initialThread;

    public ZkScheduleManager() {
        this.currentScheduleServer = ScheduleServer.createScheduleServer(null);
    }

    public void init() throws Exception {
        Properties properties = new Properties();
        for (Map.Entry<String, String> e : this.zkConfig.entrySet()) {
            properties.put(e.getKey(), e.getValue());
        }
        this.init(properties);
    }

    public void reInit(final Properties p) throws Exception {
        if (this.start || Func.isNotEmpty(this.hearBeatScheduler)) {
            throw new Exception("调度器有任务处理，不能重新初始化");
        }
        this.init(p);
    }

    public void init(final Properties p) throws Exception {
        if (Func.isNotEmpty(this.initialThread)) {
            this.initialThread.stopThread();
        }
        this.initLock.lock();
        try {
            this.scheduleDataManager = null;
            if (Func.isNotEmpty(this.zkManager)) {
                this.zkManager.close();
            }
            this.zkManager = new ZkManager(p);
            this.errorMessage = "Zookeeper connecting ......" + this.zkManager.getConnectStr();
            initialThread = new InitialThread(this);
            initialThread.setName("ScheduleManager-initialThread");
            initialThread.start();
        } finally {
            this.initLock.unlock();
        }
    }

    private void rewriteScheduleInfo() throws Exception {
        registerLock.lock();
        try {
            if (this.isStopSchedule) {
                if (log.isDebugEnabled()) {
                    log.debug("外部命令终止调度,不在注册调度服务，避免遗留垃圾数据：" + currentScheduleServer.getUuid());
                }
                return;
            }
            // 先发送心跳信息
            if (Func.isNotEmpty(errorMessage)) {
                this.currentScheduleServer.setDealInfoDesc(errorMessage);
            }
            if (!this.scheduleDataManager.refreshScheduleServer(this.currentScheduleServer)) {
                // 更新信息失败，清除内存数据后重新注册
                this.clearMemoInfo();
                this.scheduleDataManager.registerScheduleServer(this.currentScheduleServer);
            }
            isScheduleServerRegister = true;
        } finally {
            registerLock.unlock();
        }
    }

    /**
     * 清除内存中所有的已经取得的数据和任务队列,在心态更新失败，或者发现注册中心的调度信息被删除
     */
    public void clearMemoInfo() {
    }

    /**
     * 根据当前调度服务器的信息，重新计算分配所有的调度任务
     * 任务的分配是需要加锁，避免数据分配错误。为了避免数据锁带来的负面作用，通过版本号来达到锁的目的
     * <p/>
     * 1、获取任务状态的版本号
     * 2、获取所有的服务器注册信息和任务队列信息
     * 3、清除已经超过心跳周期的服务器注册信息
     * 3、重新计算任务分配
     * 4、更新任务状态的版本号【乐观锁】
     * 5、根系任务队列的分配信息
     *
     * @throws Exception
     */
    public void assignScheduleTask() throws Exception {
        scheduleDataManager.clearExpireScheduleServer();
        List<String> serverList = scheduleDataManager.loadScheduleServerNames();
        if (!scheduleDataManager.isLeader(this.currentScheduleServer.getUuid(), serverList)) {
            if (log.isDebugEnabled()) {
                log.debug(this.currentScheduleServer.getUuid() + ":不是负责任务分配的Leader,直接返回");
            }
            return;
        }
        //黑名单
        for (String ip : zkManager.getIpBlacklist()) {
            int index = serverList.indexOf(ip);
            if (index > -1) {
                serverList.remove(index);
            }
        }
        // 设置初始化成功标准，避免在leader转换的时候，新增的线程组初始化失败
        scheduleDataManager.assignTask(this.currentScheduleServer.getUuid(), serverList);
    }

    /**
     * 定时向数据配置中心更新当前服务器的心跳信息。
     * 如果发现本次更新的时间如果已经超过了，服务器死亡的心跳周期，则不能在向服务器更新信息。
     * 而应该当作新的服务器，进行重新注册。
     *
     * @throws Exception
     */
    public void refreshScheduleServer() throws Exception {
        try {
            rewriteScheduleInfo();
            // 如果任务信息没有初始化成功，不做任务相关的处理
            if (!this.isScheduleServerRegister) {
                return;
            }

            // 重新分配任务
            this.assignScheduleTask();
            // 检查本地任务
            this.checkLocalTask();
        } catch (Throwable e) {
            // 清除内存中所有的已经取得的数据和任务队列,避免心跳线程失败时候导致的数据重复
            this.clearMemoInfo();
            if (e instanceof Exception) {
                throw (Exception) e;
            } else {
                throw new Exception(e.getMessage(), e);
            }
        }
    }

    public void checkLocalTask() throws Exception {
        // 检查系统任务执行情况
        scheduleDataManager.checkLocalTask(this.currentScheduleServer.getUuid());
    }

    /**
     * 在Zk状态正常后回调数据初始化
     *
     * @throws Exception
     */
    public void initialData() throws Exception {
        this.zkManager.initial();
        this.scheduleDataManager = new ScheduleDataManager4Zk(this.zkManager);
        checkScheduleDataManager();
        if (this.start) {
            // 注册调度管理器
            this.scheduleDataManager.registerScheduleServer(this.currentScheduleServer);
            hearBeatScheduler.schedule(new HeartBeatTimerTask(this), this.timerInterval, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * 执行封装:根据ZK判断是否执行,调度仍然受Spring-Schedule控制
     *
     * @param task
     * @return
     */
    private Runnable taskWrapper(final Runnable task) {
        return new Runnable() {
            @Override
            public void run() {
                TaskDefine taskDefine = resolveTaskName(task);
                String name = taskDefine.stringKey();
                if (StrKit.isNotEmpty(name)) {
                    boolean isOwner = false;
                    boolean isRunning = true;
                    try {
                        if (!isScheduleServerRegister) {
                            Thread.sleep(NUM_1000);
                        }
                        if (zkManager.checkZookeeperState()) {
                            isOwner = scheduleDataManager.isOwner(name, currentScheduleServer.getUuid());
                            isOwnerMap.put(name, isOwner);
                            isRunning = scheduleDataManager.isRunning(name);
                        } else {
                            // 如果zk不可用，使用历史数据
                            if (Func.isNotEmpty(isOwnerMap)) {
                                isOwner = isOwnerMap.get(name);
                            }
                        }
                        if (isOwner && isRunning) {
                            String msg = null;
                            try {
                                task.run();
                                log.info("Cron job has been executed.");
                            } catch (Exception e) {
                                msg = e.getLocalizedMessage();
                            }
                            scheduleDataManager.saveRunningInfo(name, currentScheduleServer.getUuid(), msg);
                        }
                    } catch (Exception e) {
                        log.error("Check task owner error.", e);
                    }
                }
            }
        };
    }

    private TaskDefine resolveTaskName(final Runnable task) {
        Method targetMethod;
        TaskDefine taskDefine = new TaskDefine();
        if (task instanceof ScheduledMethodRunnable) {
            ScheduledMethodRunnable uncodeScheduledMethodRunnable = (ScheduledMethodRunnable) task;
            targetMethod = uncodeScheduledMethodRunnable.getMethod();
            taskDefine.setType(TaskDefine.TYPE_UNCODE_TASK);
            if (StrKit.isNotBlank(uncodeScheduledMethodRunnable.getExtKeySuffix())) {
                taskDefine.setExtKeySuffix(uncodeScheduledMethodRunnable.getExtKeySuffix());
            }
        } else {
            org.springframework.scheduling.support.ScheduledMethodRunnable springScheduledMethodRunnable = (org.springframework.scheduling.support.ScheduledMethodRunnable) task;
            targetMethod = springScheduledMethodRunnable.getMethod();
            taskDefine.setType(TaskDefine.TYPE_SPRING_TASK);
        }
        String[] beanNames = applicationContext.getBeanNamesForType(targetMethod.getDeclaringClass());
        if (Func.isNotEmpty(beanNames)) {
            taskDefine.setTargetBean(beanNames[0]);
            taskDefine.setTargetMethod(targetMethod.getName());
        }
        return taskDefine;
    }

    /**
     * 心跳检测任务
     */
    static class HeartBeatTimerTask extends java.util.TimerTask {

        ZkScheduleManager manager;

        HeartBeatTimerTask(final ZkScheduleManager aManager) {
            manager = aManager;
        }

        @Override
        public void run() {
            try {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
                manager.refreshScheduleServer();
            } catch (Exception ex) {
                log.error("HeartBeatTimerTask error ", ex);
            }
        }
    }

    /**
     * 初始化线程
     */
    static class InitialThread extends Thread {

        boolean isStop = false;

        ZkScheduleManager sm;

        InitialThread(final ZkScheduleManager sm) {
            this.sm = sm;
        }

        public void stopThread() {
            this.isStop = true;
        }

        @Override
        public void run() {
            sm.initLock.lock();
            try {
                int count = 0;
                while (!sm.zkManager.checkZookeeperState()) {
                    count = count + 1;
                    if (count % 50 == 0) {
                        sm.errorMessage = "Zookeeper connecting ......" + sm.zkManager.getConnectStr() + " spendTime:" + count * 20 + "(ms)";
                        log.error(sm.errorMessage);
                    }
                    Thread.sleep(20);
                    if (this.isStop) {
                        return;
                    }
                }
                sm.initialData();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            } finally {
                sm.initLock.unlock();
            }

        }

    }

    public IScheduleDataManager getScheduleDataManager() {
        return scheduleDataManager;
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationcontext) throws BeansException {
        if (Func.isEmpty(ZkScheduleManager.applicationContext)) {
            ZkScheduleManager.applicationContext = applicationcontext;
        }
    }

    public void setZkManager(final ZkManager zkManager) {
        this.zkManager = zkManager;
    }

    public ZkManager getZkManager() {
        return zkManager;
    }

    public void setZkConfig(final Map<String, String> zkConfig) {
        this.zkConfig = zkConfig;
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable task, final long period) {
        registerScheduleTask(task, period);
        return super.scheduleAtFixedRate(taskWrapper(task), period);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable task, final long delay) {
        registerScheduleTask(task, delay);
        return super.scheduleWithFixedDelay(taskWrapper(task), delay);
    }

    private void registerScheduleTask(final Runnable task, final long period) {
        try {
            TaskDefine taskDefine = resolveTaskName(task);
            taskDefine.setPeriod(period);
            checkScheduleDataManager();
            scheduleDataManager.addTask(taskDefine);
            log.debug(currentScheduleServer.getUuid() + ":自动向集群注册任务[" + taskDefine.stringKey() + "]");
        } catch (Exception e) {
            log.error("update task error", e);
        }
    }

    @Override
    public ScheduledFuture<?> schedule(final Runnable task, final Trigger trigger) {
        try {
            TaskDefine taskDefine = resolveTaskName(task);
            String cronEx = trigger.toString();
            int index = cronEx.indexOf(":");
            if (index >= 0) {
                cronEx = cronEx.substring(index + 1);
                taskDefine.setCronExpression(cronEx.trim());
            }
            checkScheduleDataManager();
            scheduleDataManager.addTask(taskDefine);
            log.debug(currentScheduleServer.getUuid() + ":自动向集群注册任务[" + taskDefine.stringKey() + "]");
        } catch (Exception e) {
            log.error("update task error", e);
        }
        return super.schedule(taskWrapper(task), trigger);
    }

    @Override
    public ScheduledFuture<?> schedule(final Runnable task, final Date startTime) {
        try {
            TaskDefine taskDefine = resolveTaskName(task);
            taskDefine.setStartTime(startTime);
            checkScheduleDataManager();
            scheduleDataManager.addTask(taskDefine);
            log.debug(currentScheduleServer.getUuid() + ":自动向集群注册任务[" + taskDefine.stringKey() + "]");
        } catch (Exception e) {
            log.error("update task error", e);
        }
        return super.schedule(taskWrapper(task), startTime);
    }

    private void checkScheduleDataManager() throws InterruptedException {
        while (Func.isEmpty(scheduleDataManager)) {
            if (downLatch.await(NUM_1000, TimeUnit.MILLISECONDS)) {
                log.debug("await finished");
            }
        }
        downLatch.countDown();
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable task, final Date startTime, final long period) {
        registerScheduleTaskWithStartTime(task, startTime, period);
        return super.scheduleAtFixedRate(taskWrapper(task), startTime, period);
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable task, final Date startTime, final long delay) {
        registerScheduleTaskWithStartTime(task, startTime, delay);
        return super.scheduleWithFixedDelay(taskWrapper(task), startTime, delay);
    }

    private void registerScheduleTaskWithStartTime(final Runnable task, final Date startTime, final long period) {
        try {
            TaskDefine taskDefine = resolveTaskName(task);
            taskDefine.setStartTime(startTime);
            taskDefine.setPeriod(period);
            checkScheduleDataManager();
            scheduleDataManager.addTask(taskDefine);
            log.debug(currentScheduleServer.getUuid() + ":自动向集群注册任务[" + taskDefine.stringKey() + "]");
        } catch (Exception e) {
            log.error("update task error", e);
        }
    }

    public boolean checkAdminUser(final String account, final String password) {
        if (StrKit.isBlank(account) || StrKit.isBlank(password)) {
            return false;
        }
        String name = zkConfig.get(ZkManager.Keys.userName.toString());
        String pwd = zkConfig.get(ZkManager.Keys.password.toString());
        if (account.equals(name) && password.equals(pwd)) {
            return true;
        }
        return false;
    }

    public String getScheduleServerUUid() {
        if (Func.isNotEmpty(currentScheduleServer)) {
            return currentScheduleServer.getUuid();
        }
        return StrKit.S_EMPTY;
    }

    public Map<String, Boolean> getIsOwnerMap() {
        return isOwnerMap;
    }

    public static ApplicationContext getApplicationContext() {
        return ZkScheduleManager.applicationContext;
    }

    @Override
    public void destroy() {
        try {
            if (Func.isNotEmpty(this.initialThread)) {
                this.initialThread.stopThread();
            }

            if (Func.isNotEmpty(this.scheduleDataManager)) {
                this.scheduleDataManager.clearExpireScheduleServer();
            }
            if (Func.isNotEmpty(this.hearBeatScheduler)) {
                this.hearBeatScheduler.shutdown();
            }
        } catch (Exception e) {
            log.error("destroy ZKScheduleManager error", e);
        } finally {
            if (Func.isNotEmpty(this.zkManager)) {
                try {
                    this.zkManager.close();
                } catch (Exception e) {
                    log.error("close ZKScheduleManager error", e);
                }
            }
        }
    }
}
