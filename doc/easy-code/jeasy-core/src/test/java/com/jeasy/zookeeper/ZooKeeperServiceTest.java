package com.jeasy.zookeeper;

import com.jeasy.common.zookeeper.ZooKeeperService;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ZooKeeperServiceTest {

    private ZooKeeperService zooKeeperService;

    @Before
    public void before() {
        try {
            zooKeeperService = new ZooKeeperService("192.168.1.156:2181");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSubPaths() {
        try {
            List<String> paths = zooKeeperService.getSubPaths("/summall/conf");
            for (String path : paths) {
                System.out.println("path:" + path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePathValue() throws Exception {
        zooKeeperService.updatePathValue("/test/path", "mypath!");
    }

    @Test
    public void createPath() throws Exception {
        zooKeeperService.createPath("/test/path", "test data");
    }

    @Test
    public void deletePath() throws Exception {
        zooKeeperService.deletePath("/test/path");
    }

    @Test
    public void getPathValue() {
        try {
            System.out.println(zooKeeperService.getPathValue("/test/path"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void distriguteLock() {
        final String path = "/lock_test";

        new Thread() {

            public void run() {
                final StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                try {
                    zooKeeperService.distributeLock(path, 10000, new Runnable() {

                        @Override
                        public void run() {
                            stopWatch.stop();
                            System.out.println("执行任务---1---，获得锁耗时:" + stopWatch.getTime());
                            try {
                                Thread.sleep(15000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {

            public void run() {
                final StopWatch stopWatch = new StopWatch();
                stopWatch.start();
                try {
                    zooKeeperService.distributeLock(path, 10000, new Runnable() {

                        @Override
                        public void run() {
                            stopWatch.stop();
                            System.out.println("执行任务---2---，获得锁耗时:" + stopWatch.getTime());
                            try {
                                Thread.sleep(15000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
