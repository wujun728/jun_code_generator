package com.jeasy.common.zookeeper;

import com.google.common.collect.Maps;
import com.jeasy.common.str.StrKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * zookeeper配置<br>
 * 继承spring加载上下文属性文件的类<br>
 * 如果properties中的属性名与zookeeper中的一样，那么参数值将会被zookeeper上的值覆盖。<br>
 * properties文件配置两个参数:<br>
 * zk.servers=192.168.1.156:2181,192.168.1.120:2181 <br>
 * #zk.config.root.path defaut value id "/cn/com/easy/config",u could delete the
 * set<br>
 * #可选，默认为/cn/com/easy/config<br>
 * zk.config.root.path=/cn/com/easy/config<br>
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class ZooKeeperPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    /**
     * zookeeper服务器地址 的properties参数名,在properties文件中设置
     */
    private final String ZOOKEEPER_SERVERS_PRO = "zk.servers";

    /**
     * 根节点路径
     */
    private String rootPath = StrKit.S_EMPTY;

    @Override
    protected void processProperties(final ConfigurableListableBeanFactory beanFactoryToProcess, final Properties props) throws BeansException {
        try {
            // zookeeper服务器
            String zookeeperServers = props.getProperty(ZOOKEEPER_SERVERS_PRO);
            // 配置的根节点
            String configRootPath = this.getRootPath();
            Map<String, String> customProperties = this.getConfigurationInZookeeper(zookeeperServers, configRootPath);
            props.putAll(customProperties);
            log.debug(props.toString());
            super.processProperties(beanFactoryToProcess, props);
        } catch (Exception e) {
            log.error("从Zookeeper获取配置异常!" + e.getMessage(), e);
        }
    }

    /**
     * 获取zookeeper中的配置数据
     *
     * @param zookeeperServers
     * @param configRootPath
     * @return
     * @throws Exception
     */
    private Map<String, String> getConfigurationInZookeeper(final String zookeeperServers, final String configRootPath) throws Exception {
        // 服务器地址不能为空
        if (StrKit.isBlank(zookeeperServers)) {
            throw new Exception("Zookeeper服务器地址不能为空！");
        }
        // 根节点不能为空
        if (StrKit.isBlank(configRootPath)) {
            throw new Exception("Zookeeper配置根节点不能为空！");
        }
        // 属性名，属性值对应的map
        Map<String, String> propertiesInZkMap = Maps.newHashMap();
        //
        ZooKeeperService zooKeeperService = new ZooKeeperService(zookeeperServers);
        // 获取所有子节点
        List<String> paths = zooKeeperService.getSubPaths(configRootPath);
        // 遍历所有子节点，以及节点值
        if (CollectionUtils.isNotEmpty(paths)) {
            // 遍历有子节点
            for (String path : paths) {
                String data = zooKeeperService.getPathValue(configRootPath + "/" + path);
                if (StrKit.isNotBlank(data)) {
                    propertiesInZkMap.put(path, data);
                }
            }
        }
        zooKeeperService.close();
        return propertiesInZkMap;
    }

    /**
     * get 根节点路径
     *
     * @return
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     * set 根节点路径
     *
     * @param rootPath
     */
    public void setRootPath(final String rootPath) {
        this.rootPath = rootPath;
    }
}
