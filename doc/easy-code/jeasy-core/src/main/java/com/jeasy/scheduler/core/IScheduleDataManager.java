package com.jeasy.scheduler.core;

import java.util.List;

/**
 * 调度配置中心客户端接口，可以有基于数据库的实现，可以有基于ConfigServer的实现
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface IScheduleDataManager {

    /**
     * 发送心跳信息
     *
     * @param server
     * @return
     * @throws Exception
     */
    boolean refreshScheduleServer(ScheduleServer server) throws Exception;

    /**
     * 注册服务器
     *
     * @param server
     * @throws Exception
     */
    void registerScheduleServer(ScheduleServer server) throws Exception;

    /**
     * isLeader
     *
     * @param uuid
     * @param serverList
     * @return
     */
    boolean isLeader(String uuid, List<String> serverList);

    /**
     * unRegisterScheduleServer
     *
     * @param server
     * @throws Exception
     */
    void unRegisterScheduleServer(ScheduleServer server) throws Exception;

    /**
     * clearExpireScheduleServer
     *
     * @throws Exception
     */
    void clearExpireScheduleServer() throws Exception;

    /**
     * loadScheduleServerNames
     *
     * @return
     * @throws Exception
     */
    List<String> loadScheduleServerNames() throws Exception;

    /**
     * assignTask
     *
     * @param currentUuid
     * @param taskServerList
     * @throws Exception
     */
    void assignTask(String currentUuid, List<String> taskServerList) throws Exception;

    /**
     * isOwner
     *
     * @param name
     * @param uuid
     * @return
     * @throws Exception
     */
    boolean isOwner(String name, String uuid) throws Exception;

    /**
     * isRunning
     *
     * @param name
     * @return
     * @throws Exception
     */
    boolean isRunning(String name) throws Exception;

    /**
     * addTask
     *
     * @param taskDefine
     * @throws Exception
     */
    void addTask(TaskDefine taskDefine) throws Exception;

    /**
     * updateTask
     *
     * @param taskDefine
     * @throws Exception
     */
    void updateTask(TaskDefine taskDefine) throws Exception;

    /**
     * addTask中存储的Key由对象本身的字符串组成，此方法实现重载
     *
     * @param targetBean
     * @param targetMethod
     * @throws Exception
     */
    void delTask(String targetBean, String targetMethod) throws Exception;

    /**
     * delTask
     *
     * @param taskDefine
     * @throws Exception
     */
    void delTask(TaskDefine taskDefine) throws Exception;

    /**
     * selectTask
     *
     * @return
     * @throws Exception
     */
    List<TaskDefine> selectTask() throws Exception;

    /**
     * checkLocalTask
     *
     * @param currentUuid
     * @return
     * @throws Exception
     */
    boolean checkLocalTask(String currentUuid) throws Exception;

    /**
     * isExistsTask
     *
     * @param taskDefine
     * @return
     * @throws Exception
     */
    boolean isExistsTask(TaskDefine taskDefine) throws Exception;

    /**
     * saveRunningInfo
     *
     * @param name
     * @param uuid
     * @param msg
     * @return
     * @throws Exception
     */
    boolean saveRunningInfo(String name, String uuid, String msg) throws Exception;

    /**
     * saveRunningInfo
     *
     * @param name
     * @param uuid
     * @return
     * @throws Exception
     */
    boolean saveRunningInfo(String name, String uuid) throws Exception;
}
