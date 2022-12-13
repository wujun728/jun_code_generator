package com.jeasy.common.zookeeper;

/**
 * zookeeper 节点监听，要继承此接口
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface IZookeeperWatch {

    /**
     * 获取要监听的节点
     *
     * @return
     */
    String getWatchPath();

    /**
     * 应用启动，注删监听时，会先从zookeeper服务器上加载节点值,这个方法用来捕获这个值<br>
     * 还有一种情况是，如果当前监听的节点不存在，然后当创建节点时，这个方法会被触发，会返回这个初始值
     *
     * @param data
     */
    void handLoad(String data);

    /**
     * 接收节点变化
     *
     * @param data
     */
    void handChange(String data);
}
