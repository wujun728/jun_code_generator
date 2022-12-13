package com.jeasy.scheduler.core;

import com.jeasy.common.str.StrKit;
import com.jeasy.scheduler.util.ScheduleUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.util.UUID;

/**
 * 调度服务器信息定义
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
@Data
public class ScheduleServer {
    /**
     * 全局唯一编号
     */
    private String uuid;

    private String ownSign;
    /**
     * 机器IP地址
     */
    private String ip;

    /**
     * 机器名称
     */
    private String hostName;

    /**
     * 服务开始时间
     */
    private Timestamp registerTime;
    /**
     * 最后一次心跳通知时间
     */
    private Timestamp heartBeatTime;
    /**
     * 最后一次取数据时间
     */
    private Timestamp lastFetchDataTime;
    /**
     * 处理描述信息，例如读取的任务数量，处理成功的任务数量，处理失败的数量，处理耗时
     * FetchDataCount=4430,FetcheDataNum=438570,DealDataSucess=438570,DealDataFail=0,DealSpendTime=651066
     */
    private String dealInfoDesc;

    private String nextRunStartTime;

    private String nextRunEndTime;
    /**
     * 配置中心的当前时间
     */
    private Timestamp centerServerTime;

    /**
     * 数据版本号
     */
    private long version;

    private boolean isRegister;

    public ScheduleServer() {

    }

    public static ScheduleServer createScheduleServer(final String aOwnSign) {
        long currentTime = System.currentTimeMillis();
        ScheduleServer result = new ScheduleServer();
        result.ownSign = aOwnSign;
        result.ip = ScheduleUtil.getLocalIP();
        result.hostName = ScheduleUtil.getLocalHostName();
        result.registerTime = new Timestamp(currentTime);
        result.heartBeatTime = null;
        result.dealInfoDesc = "调度初始化";
        result.version = 0;
        result.uuid = result.ip
            + StrKit.S_DOLLAR
            + (UUID.randomUUID().toString().replaceAll(StrKit.S_MIDDLELINE, StrKit.S_EMPTY)
            .toUpperCase());
        return result;
    }
}
