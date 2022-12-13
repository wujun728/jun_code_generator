package com.jeasy.zookeeper;

import com.jeasy.common.zookeeper.IZookeeperWatch;
import com.jeasy.common.zookeeper.ZooKeeperService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/zookeeper/spring-zookeeper-watch.xml")
public class ZookeeperWatchServiceTest {

    @Autowired
    private ZooKeeperService zookeeperService;

    private String path = "/summall/conf/jdbc.username/aa";

    @Test
    public void addNodeWatch() throws Exception {
        try {
            zookeeperService.addNodeWatch(new IZookeeperWatch() {

                @Override
                public void handChange(String data) {
                    System.out.println("获取到数据变化-1" + path + "：" + data);
                }

                @Override
                public String getWatchPath() {
                    return path;
                }

                @Override
                public void handLoad(String data) {
                    System.out.println("获取到数据Loaded-1" + path + "：" + data);

                }

            });
            IZookeeperWatch zookeeperWatch = new IZookeeperWatch() {

                @Override
                public void handChange(String data) {
                    System.out.println("获取到数据变化-2" + path + "：" + data);
                }

                @Override
                public String getWatchPath() {
                    return path;
                }

                @Override
                public void handLoad(String data) {
                    System.out.println("获取到数据Loaded-2" + path + "：" + data);
                }

            };
            zookeeperService.addNodeWatch(zookeeperWatch);
            Thread.sleep(10000);
            // 移除监听
            zookeeperService.removeNodeWatch(zookeeperWatch);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void setPathValue() throws Exception {
        zookeeperService.updatePathValue(path, "mypath.......");
    }
}
