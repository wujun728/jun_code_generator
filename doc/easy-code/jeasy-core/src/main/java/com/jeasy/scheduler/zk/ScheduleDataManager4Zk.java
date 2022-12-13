package com.jeasy.scheduler.zk;

import com.google.gson.*;
import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.date.DateKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.scheduler.DynamicTaskManager;
import com.jeasy.scheduler.core.IScheduleDataManager;
import com.jeasy.scheduler.core.ScheduleServer;
import com.jeasy.scheduler.core.TaskDefine;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * zk实现类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public class ScheduleDataManager4Zk implements IScheduleDataManager {

    private static final String NODE_SERVER = "server";
    private static final String NODE_TASK = "task";
    public static final int NUM_5000 = 5000;
    private static final long SERVER_EXPIRE_TIME = NUM_5000 * 3;

    private final Gson gson;
    private final ZkManager zkManager;
    private final String pathServer;
    private final String pathTask;
    private final long zkBaseTime;
    private final long localBaseTime;
    private final Random random;

    public ScheduleDataManager4Zk(final ZkManager aZkManager) throws Exception {
        this.zkManager = aZkManager;
        gson = new GsonBuilder().registerTypeAdapter(Timestamp.class, new TimestampTypeAdapter()).setDateFormat(DateKit.NORM_DATETIME_PATTERN).create();
        this.pathServer = this.zkManager.getRootPath() + StrKit.S_SLASH + NODE_SERVER;
        this.pathTask = this.zkManager.getRootPath() + StrKit.S_SLASH + NODE_TASK;
        this.random = new Random();

        if (Func.isEmpty(this.getZooKeeper().exists(this.pathServer, false))) {
            ZkTools.createPath(getZooKeeper(), this.pathServer, CreateMode.PERSISTENT, this.zkManager.getAcl());
        }

        if (Func.isEmpty(this.getZooKeeper().exists(this.pathTask, false))) {
            ZkTools.createPath(getZooKeeper(), this.pathTask, CreateMode.PERSISTENT, this.zkManager.getAcl());
        }

        localBaseTime = System.currentTimeMillis();
        String tempPath = this.zkManager.getZooKeeper().create(this.zkManager.getRootPath() + "/systime", null, this.zkManager.getAcl(), CreateMode.EPHEMERAL_SEQUENTIAL);
        Stat tempStat = this.zkManager.getZooKeeper().exists(tempPath, false);
        zkBaseTime = tempStat.getCtime();
        ZkTools.deleteTree(getZooKeeper(), tempPath);
        if (Math.abs(this.zkBaseTime - this.localBaseTime) > NUM_5000) {
            log.error("请注意，Zookeeper服务器时间与本地时间相差 ： " + Math.abs(this.zkBaseTime - this.localBaseTime) + " ms");
        }
    }

    private ZooKeeper getZooKeeper() throws Exception {
        return this.zkManager.getZooKeeper();
    }

    @Override
    public final boolean refreshScheduleServer(final ScheduleServer server) throws Exception {
        Timestamp heartBeatTime = new Timestamp(this.getSystemTime());
        String zkPath = this.pathServer + StrKit.S_SLASH + server.getUuid();
        if (Func.isEmpty(this.getZooKeeper().exists(zkPath, false))) {
            // 数据可能被清除，先清除内存数据后，重新注册数据
            server.setRegister(false);
            return false;
        } else {
            Timestamp oldHeartBeatTime = server.getHeartBeatTime();
            server.setHeartBeatTime(heartBeatTime);
            server.setVersion(server.getVersion() + 1);
            String valueString = this.gson.toJson(server);
            try {
                this.getZooKeeper().setData(zkPath, valueString.getBytes(CharsetKit.DEFAULT_CHARSET), -1);
            } catch (Exception e) {
                // 恢复上次的心跳时间
                server.setHeartBeatTime(oldHeartBeatTime);
                server.setVersion(server.getVersion() - 1);
                throw e;
            }
            return true;
        }
    }

    @Override
    public void registerScheduleServer(final ScheduleServer server) throws Exception {
        if (server.isRegister()) {
            throw new Exception(server.getUuid() + " 被重复注册");
        }
        // clearExpireScheduleServer();
        String realPath;
        // 此处必须增加UUID作为唯一性保障

        String zkServerPath = pathServer + StrKit.S_SLASH + server.getIp() + "$" + UUID.randomUUID().toString().replaceAll("-", StrKit.S_EMPTY).toUpperCase() + "$";
        realPath = this.getZooKeeper().create(zkServerPath, null, this.zkManager.getAcl(), CreateMode.PERSISTENT_SEQUENTIAL);
        server.setUuid(realPath.substring(realPath.lastIndexOf(StrKit.S_SLASH) + 1));

        Timestamp heartBeatTime = new Timestamp(getSystemTime());
        server.setHeartBeatTime(heartBeatTime);

        String valueString = this.gson.toJson(server);
        this.getZooKeeper().setData(realPath, valueString.getBytes(CharsetKit.DEFAULT_CHARSET), -1);
        server.setRegister(true);
    }

    public final List<String> loadAllScheduleServer() throws Exception {
        List<String> names = this.getZooKeeper().getChildren(this.pathServer, false);
        Collections.sort(names);
        return names;
    }

    @Override
    public final void clearExpireScheduleServer() throws Exception {
        String zkPath = this.pathServer;
        if (Func.isEmpty(this.getZooKeeper().exists(zkPath, false))) {
            this.getZooKeeper().create(zkPath, null, this.zkManager.getAcl(), CreateMode.PERSISTENT);
        }
        for (String name : this.zkManager.getZooKeeper().getChildren(zkPath, false)) {
            try {
                Stat stat = new Stat();
                this.getZooKeeper().getData(zkPath + StrKit.S_SLASH + name, null, stat);
                if (getSystemTime() - stat.getMtime() > SERVER_EXPIRE_TIME) {
                    ZkTools.deleteTree(this.getZooKeeper(), zkPath + StrKit.S_SLASH + name);
                    log.debug("ScheduleServer[" + zkPath + StrKit.S_SLASH + name + "]过期清除");
                }
            } catch (Exception e) {
                // 当有多台服务器时，存在并发清理的可能，忽略异常
                log.error("clearExpireScheduleServer error", e);
            }
        }
    }

    @Override
    public void unRegisterScheduleServer(final ScheduleServer server) throws Exception {
        List<String> serverList = this.loadScheduleServerNames();

        if (server.isRegister() && this.isLeader(server.getUuid(), serverList)) {
            //delete task
            String zkPath = this.pathTask;
            String serverPath = this.pathServer;

            if (Func.isEmpty(this.getZooKeeper().exists(zkPath, false))) {
                this.getZooKeeper().create(zkPath, null, this.zkManager.getAcl(), CreateMode.PERSISTENT);
            }

            //get all task
            List<String> children = this.getZooKeeper().getChildren(zkPath, false);
            if (null != children && children.size() > 0) {
                for (String taskName : children) {
                    String taskPath = zkPath + StrKit.S_SLASH + taskName;
                    if (Func.isNotEmpty(this.getZooKeeper().exists(taskPath, false))) {
                        ZkTools.deleteTree(this.getZooKeeper(), taskPath + StrKit.S_SLASH + server.getUuid());
                    }
                }
            }

            //删除
            if (Func.isEmpty(this.getZooKeeper().exists(this.pathServer, false))) {
                ZkTools.deleteTree(this.getZooKeeper(), serverPath + serverPath + StrKit.S_SLASH + server.getUuid());
            }
            server.setRegister(false);
        }
    }

    @Override
    public final List<String> loadScheduleServerNames() throws Exception {
        String zkPath = this.pathServer;
        if (Func.isEmpty(this.getZooKeeper().exists(zkPath, false))) {
            return new ArrayList<>();
        }
        List<String> serverList = this.getZooKeeper().getChildren(zkPath, false);
        Collections.sort(serverList, new Comparator<String>() {
            @Override
            public int compare(final String u1, final String u2) {
                return u1.substring(u1.lastIndexOf("$") + 1).compareTo(
                    u2.substring(u2.lastIndexOf("$") + 1));
            }
        });
        return serverList;
    }


    @Override
    public void assignTask(final String currentUuid, final List<String> taskServerList) throws Exception {
        if (!this.isLeader(currentUuid, taskServerList)) {
            if (log.isDebugEnabled()) {
                log.debug(currentUuid + ":不是负责任务分配的Leader,直接返回");
            }
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug(currentUuid + ":开始重新分配任务......");
        }
        if (taskServerList.size() <= 0) {
            // 在服务器动态调整的时候，可能出现服务器列表为空的清空
            return;
        }
        if (this.zkManager.checkZookeeperState()) {
            String zkPath = this.pathTask;
            if (Func.isEmpty(this.getZooKeeper().exists(zkPath, false))) {
                this.getZooKeeper().create(zkPath, null, this.zkManager.getAcl(), CreateMode.PERSISTENT);
            }
            List<String> children = this.getZooKeeper().getChildren(zkPath, false);
            if (Func.isNotEmpty(children)) {
                for (String taskName : children) {
                    String taskPath = zkPath + StrKit.S_SLASH + taskName;
                    if (Func.isEmpty(this.getZooKeeper().exists(taskPath, false))) {
                        this.getZooKeeper().create(taskPath, null, this.zkManager.getAcl(), CreateMode.PERSISTENT);
                    }

                    List<String> taskServerIds = this.getZooKeeper().getChildren(taskPath, false);
                    if (Func.isEmpty(taskServerIds)) {
                        assignServer2Task(taskServerList, taskPath);
                    } else {
                        boolean hasAssignSuccess = false;
                        for (String serverId : taskServerIds) {
                            if (taskServerList.contains(serverId)) {
                                // 防止重复分配任务，如果已经成功分配，第二个以后都删除
                                if (hasAssignSuccess) {
                                    ZkTools.deleteTree(this.getZooKeeper(), taskPath + StrKit.S_SLASH + serverId);
                                } else {
                                    hasAssignSuccess = true;
                                    continue;
                                }
                            }
                            ZkTools.deleteTree(this.getZooKeeper(), taskPath + StrKit.S_SLASH + serverId);
                        }
                        if (!hasAssignSuccess) {
                            assignServer2Task(taskServerList, taskPath);
                        }
                    }

                }
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(currentUuid + ":没有集群任务");
                }
            }
        }

    }

    private void assignServer2Task(final List<String> taskServerList, final String taskPath) throws Exception {
        int index = random.nextInt(taskServerList.size());
        String serverId = taskServerList.get(index);
        try {
            if (Func.isNotEmpty(this.getZooKeeper().exists(taskPath, false))) {
                this.getZooKeeper().create(taskPath + StrKit.S_SLASH + serverId, null, this.zkManager.getAcl(), CreateMode.PERSISTENT);
            }
        } catch (Exception e) {
            log.error("assign task error", e);
        }
        if (log.isDebugEnabled()) {
            log.debug("Assign server [" + serverId + "]" + " to task [" + taskPath + "]");
        }
    }

    @Override
    public final boolean isLeader(final String uuid, final List<String> serverList) {
        return uuid.equals(getLeader(serverList));
    }

    private String getLeader(final List<String> serverList) {
        if (Func.isEmpty(serverList)) {
            return StrKit.S_EMPTY;
        }
        long no = Long.MAX_VALUE;
        long tmpNo = -1;
        String leader = null;
        for (String server : serverList) {
            tmpNo = Long.parseLong(server.substring(server.lastIndexOf("$") + 1));
            if (no > tmpNo) {
                no = tmpNo;
                leader = server;
            }
        }
        return leader;
    }

    private long getSystemTime() {
        return this.zkBaseTime + (System.currentTimeMillis() - this.localBaseTime);
    }

    private static class TimestampTypeAdapter implements JsonSerializer<Timestamp>, JsonDeserializer<Timestamp> {
        @Override
        public JsonElement serialize(final Timestamp src, final Type arg1, final JsonSerializationContext arg2) {
            DateFormat format = new SimpleDateFormat(DateKit.NORM_DATETIME_PATTERN);
            String dateFormatAsString = format.format(new Date(src.getTime()));
            return new JsonPrimitive(dateFormatAsString);
        }

        @Override
        public Timestamp deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context) throws JsonParseException {
            if (!(json instanceof JsonPrimitive)) {
                throw new JsonParseException("The date should be a string value");
            }

            try {
                DateFormat format = new SimpleDateFormat(DateKit.NORM_DATETIME_PATTERN);
                Date date = format.parse(json.getAsString());
                return new Timestamp(date.getTime());
            } catch (Exception e) {
                throw new JsonParseException(e);
            }
        }
    }

    @Override
    public boolean isOwner(final String name, final String uuid) throws Exception {
        boolean isOwner = false;
        //查看集群中是否注册当前任务，如果没有就自动注册
        String zkPath = this.pathTask + StrKit.S_SLASH + name;
        //判断是否分配给当前节点
        if (Func.isNotEmpty(this.getZooKeeper().exists(zkPath + StrKit.S_SLASH + uuid, false))) {
            isOwner = true;
        }
        return isOwner;
    }


    @Override
    public boolean isRunning(final String name) throws Exception {
        boolean isRunning = true;
        //查看集群中是否注册当前任务，如果没有就自动注册
        String zkPath = this.pathTask + StrKit.S_SLASH + name;
        //是否手动停止
        byte[] data = this.getZooKeeper().getData(zkPath, null, null);
        if (Func.isNotEmpty(data)) {
            String json = new String(data, CharsetKit.DEFAULT_CHARSET);
            TaskDefine taskDefine = this.gson.fromJson(json, TaskDefine.class);
            if (taskDefine.isStop()) {
                isRunning = false;
            }
        }
        return isRunning;
    }

    @Override
    public boolean saveRunningInfo(final String name, final String uuid) throws Exception {
        return saveRunningInfo(name, uuid, null);
    }


    @Override
    public boolean saveRunningInfo(final String name, final String uuid, final String msg) throws Exception {
        // 查看集群中是否注册当前任务，如果没有就自动注册
        String zkPath = this.pathTask + StrKit.S_SLASH + name;
        // 判断是否分配给当前节点
        zkPath = zkPath + StrKit.S_SLASH + uuid;
        if (Func.isNotEmpty(this.getZooKeeper().exists(zkPath, false))) {
            try {
                int times = 0;
                byte[] dataVal = this.getZooKeeper().getData(zkPath, null, null);
                if (Func.isNotEmpty(dataVal)) {
                    String val = new String(dataVal, CharsetKit.DEFAULT_CHARSET);
                    String[] vals = val.split(StrKit.S_COLON);
                    times = Integer.parseInt(vals[0]);
                }
                times++;
                String newVal;
                if (StrKit.isBlank(msg)) {
                    newVal = times + StrKit.S_COLON + System.currentTimeMillis();
                } else {
                    newVal = "0:" + System.currentTimeMillis() + StrKit.S_COLON + msg;
                }
                this.getZooKeeper().setData(zkPath, newVal.getBytes(CharsetKit.DEFAULT_CHARSET), -1);
            } catch (Exception e) {
                log.error("saveRunningInfo error", e);
            }
        }
        return true;
    }

    @Override
    public boolean isExistsTask(final TaskDefine taskDefine) throws Exception {
        String zkPath = this.pathTask + StrKit.S_SLASH + taskDefine.stringKey();
        return Func.isNotEmpty(this.getZooKeeper().exists(zkPath, false));
    }

    @Override
    public void addTask(final TaskDefine taskDefine) throws Exception {
        String zkPath = this.pathTask;
        zkPath = zkPath + StrKit.S_SLASH + taskDefine.stringKey();
        if (Func.isEmpty(this.getZooKeeper().exists(zkPath, false))) {
            this.getZooKeeper().create(zkPath, null, this.zkManager.getAcl(), CreateMode.PERSISTENT);
        }
        byte[] data = this.getZooKeeper().getData(zkPath, null, null);
        if (Func.isEmpty(data)) {
            if (StrKit.isBlank(taskDefine.getType())) {
                taskDefine.setType(TaskDefine.TYPE_UNCODE_TASK);
            }
            String json = this.gson.toJson(taskDefine);
            this.getZooKeeper().setData(zkPath, json.getBytes(CharsetKit.DEFAULT_CHARSET), -1);
        }
    }

    @Override
    public void updateTask(final TaskDefine taskDefine) throws Exception {
        String zkPath = this.pathTask;
        zkPath = zkPath + StrKit.S_SLASH + taskDefine.stringKey();
        if (Func.isEmpty(this.getZooKeeper().exists(zkPath, false))) {
//            this.getZooKeeper().create(zkPath, null, this.zkManager.getAcl(), CreateMode.PERSISTENT);
//            String json = this.gson.toJson(taskDefine);
//            this.getZooKeeper().setData(zkPath, json.getBytes(), -1);
        } else {
            byte[] data = this.getZooKeeper().getData(zkPath, null, null);
            TaskDefine tmpTaskDefine;
            if (Func.isNotEmpty(data)) {
                String json = new String(data, CharsetKit.DEFAULT_CHARSET);
                tmpTaskDefine = this.gson.fromJson(json, TaskDefine.class);
                tmpTaskDefine.valueOf(tmpTaskDefine);
            } else {
                tmpTaskDefine = new TaskDefine();
            }
            tmpTaskDefine.valueOf(taskDefine);
            String json = this.gson.toJson(tmpTaskDefine);
            this.getZooKeeper().setData(zkPath, json.getBytes(CharsetKit.DEFAULT_CHARSET), -1);
        }
    }

    @Override
    public void delTask(final String targetBean, final String targetMethod) throws Exception {
        String zkPath = this.pathTask;
        if (Func.isNotEmpty(this.getZooKeeper().exists(zkPath, false))) {
            zkPath = zkPath + StrKit.S_SLASH + targetBean + "#" + targetMethod;
            if (this.getZooKeeper().exists(zkPath, false) != null) {
                ZkTools.deleteTree(this.getZooKeeper(), zkPath);
            }
        }
    }

    @Override
    public void delTask(final TaskDefine taskDefine) throws Exception {
        String zkPath = this.pathTask;
        if (Func.isNotEmpty(this.getZooKeeper().exists(zkPath, false))) {
            zkPath = zkPath + StrKit.S_SLASH + taskDefine.stringKey();
            if (this.getZooKeeper().exists(zkPath, false) != null) {
                ZkTools.deleteTree(this.getZooKeeper(), zkPath);
            }
        }
    }

    @Override
    public List<TaskDefine> selectTask() throws Exception {
        String zkPath = this.pathTask;
        List<TaskDefine> taskDefines = new ArrayList<>();
        if (Func.isNotEmpty(this.getZooKeeper().exists(zkPath, false))) {
            List<String> childes = this.getZooKeeper().getChildren(zkPath, false);
            for (String child : childes) {
                byte[] data = this.getZooKeeper().getData(zkPath + StrKit.S_SLASH + child, null, null);
                TaskDefine taskDefine;
                if (Func.isNotEmpty(data)) {
                    String json = new String(data, CharsetKit.DEFAULT_CHARSET);
                    taskDefine = this.gson.fromJson(json, TaskDefine.class);
                } else {
                    taskDefine = new TaskDefine();
                }
                String[] names = child.split("#");
                if (Func.isNotEmpty(names[0])) {
                    taskDefine.setTargetBean(names[0]);
                    taskDefine.setTargetMethod(names[1]);
                }
                List<String> sers = this.getZooKeeper().getChildren(zkPath + StrKit.S_SLASH + child, false);
                if (Func.isNotEmpty(taskDefine) && Func.isNotEmpty(sers)) {
                    taskDefine.setCurrentServer(sers.get(0));
                    byte[] dataVal = this.getZooKeeper().getData(zkPath + StrKit.S_SLASH + child + StrKit.S_SLASH + sers.get(0), null, null);
                    if (Func.isNotEmpty(dataVal)) {
                        String val = new String(dataVal, CharsetKit.DEFAULT_CHARSET);
                        String[] vals = val.split(StrKit.S_COLON);
                        taskDefine.setRunTimes(Integer.parseInt(vals[0]));
                        taskDefine.setLastRunningTime(Long.parseLong(vals[1]));
                        if (vals.length > 2 && !StrKit.isBlank(vals[2])) {
                            taskDefine.setStatus(TaskDefine.STATUS_ERROR + StrKit.S_COLON + vals[2]);
                        }
                    }
                }
                taskDefines.add(taskDefine);
            }
        }
        return taskDefines;
    }

    @Override
    public boolean checkLocalTask(final String currentUuid) throws Exception {
        if (this.zkManager.checkZookeeperState()) {
            String zkPath = this.pathTask;
            List<String> children = this.getZooKeeper().getChildren(zkPath, false);
            List<String> ownerTask = new ArrayList<>();
            if (Func.isNotEmpty(children)) {
                for (String taskName : children) {
                    if (isOwner(taskName, currentUuid)) {
                        String taskPath = zkPath + StrKit.S_SLASH + taskName;
                        byte[] data = this.getZooKeeper().getData(taskPath, null, null);
                        if (Func.isNotEmpty(data)) {
                            String json = new String(data, CharsetKit.DEFAULT_CHARSET);
                            TaskDefine td = this.gson.fromJson(json, TaskDefine.class);
                            TaskDefine taskDefine = new TaskDefine();
                            taskDefine.valueOf(td);
                            if (TaskDefine.TYPE_UNCODE_TASK.equals(taskDefine.getType())) {
                                ownerTask.add(taskName);
                                DynamicTaskManager.scheduleTask(taskDefine, new Date(getSystemTime()));
                            }
                        }
                    }
                }
            }
            DynamicTaskManager.clearLocalTask(ownerTask);
        }
        return false;
    }
}
