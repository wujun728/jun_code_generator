package com.jeasy.scheduler.zk;

import com.jeasy.common.Func;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.scheduler.core.Version;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public class ZkManager {

    private ZooKeeper zk;
    private List<ACL> acl = new ArrayList<>();
    private Properties properties;

    public enum Keys {
        /**
         * zkConnectString
         */
        zkConnectString,
        /**
         * rootPath
         */
        rootPath,
        /**
         * userName
         */
        userName,
        /**
         * password
         */
        password,
        /**
         * zkSessionTimeout
         */
        zkSessionTimeout,
        /**
         * autoRegisterTask
         */
        autoRegisterTask,
        /**
         * ipBlacklist
         */
        ipBlacklist
    }

    public ZkManager(final Properties aProperties) throws Exception {
        this.properties = aProperties;
        this.connect();
    }

    /**
     * 重连zookeeper
     *
     * @throws Exception
     */
    private synchronized void reConnection() throws Exception {
        if (Func.isNotEmpty(this.zk)) {
            this.zk.close();
            this.zk = null;
            this.connect();
        }
    }

    private void connect() throws Exception {
        CountDownLatch connectionLatch = new CountDownLatch(1);
        createZookeeper(connectionLatch);
        connectionLatch.await();
    }

    private void createZookeeper(final CountDownLatch connectionLatch) throws Exception {
        zk = new ZooKeeper(this.properties.getProperty(Keys.zkConnectString
            .toString()), Integer.parseInt(this.properties
            .getProperty(Keys.zkSessionTimeout.toString())),
            new Watcher() {
                @Override
                public void process(final WatchedEvent event) {
                    sessionEvent(connectionLatch, event);
                }
            });
        String authString = this.properties.getProperty(Keys.userName.toString())
            + StrKit.S_COLON + this.properties.getProperty(Keys.password.toString());
        zk.addAuthInfo("digest", authString.getBytes(CharsetKit.DEFAULT_CHARSET));
        acl.clear();
        acl.add(new ACL(ZooDefs.Perms.ALL, new Id("digest", DigestAuthenticationProvider.generateDigest(authString))));
        acl.add(new ACL(ZooDefs.Perms.READ, Ids.ANYONE_ID_UNSAFE));
    }

    private void sessionEvent(final CountDownLatch connectionLatch, final WatchedEvent event) {
        if (event.getState() == KeeperState.SyncConnected) {
            log.info("收到ZK连接成功事件！");
            connectionLatch.countDown();
        } else if (event.getState() == KeeperState.Expired) {
            log.error("会话超时，等待重新建立ZK连接...");
            try {
                reConnection();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        // Disconnected：Zookeeper会自动处理Disconnected状态重连
    }

    public final void close() throws InterruptedException {
        log.info("关闭zookeeper连接");
        this.zk.close();
    }

    public final String getRootPath() {
        return this.properties.getProperty(Keys.rootPath.toString());
    }

    public final List<String> getIpBlacklist() {
        List<String> ips = new ArrayList<String>();
        String list = this.properties.getProperty(Keys.ipBlacklist.toString());
        if (StrKit.isNotBlank(list)) {
            ips = Arrays.asList(list.split(","));
        }
        return ips;
    }

    public final String getConnectStr() {
        return this.properties.getProperty(Keys.zkConnectString.toString());
    }

    public final boolean isAutoRegisterTask() {
        String autoRegisterTask = this.properties.getProperty(Keys.autoRegisterTask.toString());
        if (StrKit.isNotBlank(autoRegisterTask)) {
            return Boolean.valueOf(autoRegisterTask);
        }
        return true;
    }

    public final boolean checkZookeeperState() throws Exception {
        return zk != null && zk.getState() == States.CONNECTED;
    }

    public final void initial() throws Exception {
        //当zk状态正常后才能调用
        checkParent(zk, this.getRootPath());
        if (Func.isEmpty(zk.exists(this.getRootPath(), false))) {
            ZkTools.createPath(zk, this.getRootPath(), CreateMode.PERSISTENT, acl);
            //设置版本信息
            zk.setData(this.getRootPath(), Version.getVersion().getBytes(CharsetKit.DEFAULT_CHARSET), -1);
        } else {
            //先校验父亲节点，本身是否已经是schedule的目录
            byte[] value = zk.getData(this.getRootPath(), false, null);
            if (Func.isEmpty(value)) {
                zk.setData(this.getRootPath(), Version.getVersion().getBytes(CharsetKit.DEFAULT_CHARSET), -1);
            } else {
                String dataVersion = new String(value, CharsetKit.DEFAULT_CHARSET);
                if (!Version.isCompatible(dataVersion)) {
                    throw new Exception("TBSchedule程序版本 " + Version.getVersion() + " 不兼容Zookeeper中的数据版本 " + dataVersion);
                }
                log.info("当前的程序版本:" + Version.getVersion() + " 数据版本: " + dataVersion);
            }
        }
    }

    private static void checkParent(final ZooKeeper zk, final String path) throws Exception {
        String[] list = path.split(StrKit.S_SLASH);
        String zkPath = StrKit.S_EMPTY;
        for (int i = 0; i < list.length - 1; i++) {
            String str = list[i];
            if (StrKit.isNotEmpty(str)) {
                zkPath = zkPath + StrKit.S_SLASH + str;
                if (Func.isNotEmpty(zk.exists(zkPath, false))) {
                    byte[] value = zk.getData(zkPath, false, null);
                    if (Func.isNotEmpty(value) && new String(value, CharsetKit.DEFAULT_CHARSET).contains("uncode-schedule-")) {
                        throw new Exception("\"" + zkPath + "\"  is already a schedule instance's root directory, its any subdirectory cannot as the root directory of others");
                    }
                }
            }
        }
    }

    public final List<ACL> getAcl() {
        return acl;
    }

    public final ZooKeeper getZooKeeper() throws Exception {
        if (!this.checkZookeeperState()) {
            reConnection();
        }
        return this.zk;
    }
}
