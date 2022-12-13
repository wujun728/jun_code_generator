package com.jeasy.scheduler.config;

import com.google.common.collect.Maps;
import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/9/22 14:33
 */
@Slf4j
@Data
public class ScheduleConfig {

    private String zkConnect;
    private String rootPath = "/uncode/schedule";
    private int zkSessionTimeout = 60000;
    private String zkUsername;
    private String zkPassword;
    private List<String> ipBlackList;

    private List<String> quartzBean;
    private List<String> quartzMethod;
    private List<String> quartzCronExpression;

    public final Map<String, String> getConfig() {
        Map<String, String> properties = Maps.newHashMap();
        properties.put("zkConnectString", zkConnect);
        if (StrKit.isNotBlank(rootPath)) {
            properties.put("rootPath", rootPath);
        }
        if (zkSessionTimeout > 0) {
            properties.put("zkSessionTimeout", zkSessionTimeout + StrKit.S_EMPTY);
        }
        if (StrKit.isNotBlank(zkUsername)) {
            properties.put("userName", zkUsername);
        }
        if (StrKit.isNotBlank(zkPassword)) {
            properties.put("password", zkPassword);
        }
        StringBuilder sb = new StringBuilder();
        if (Func.isNotEmpty(ipBlackList)) {
            for (String ip : ipBlackList) {
                sb.append(ip).append(",");
            }
            ipBlackList.remove(sb.lastIndexOf(","));
        }
        properties.put("ipBlacklist", sb.toString());
        return properties;
    }
}
