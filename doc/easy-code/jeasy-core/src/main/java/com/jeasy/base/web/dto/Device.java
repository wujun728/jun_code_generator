package com.jeasy.base.web.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求设备
 * @author TaoBangren
 * @version 1.0
 * @since 16/8/29 下午11:56
 */
@Data
public class Device implements Serializable {

    private static final long serialVersionUID = 8462406907171754085L;

    /**
     * 平台名称
     */
    private String platform;

    /**
     * 设备名称
     */
    private String device;

    /**
     * app的版本号
     */
    private String version;

    /**
     * 设备ID/IP
     */
    private String deviceNo;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 系统版本
     */
    private String osVersion;

    /**
     * User-Agent
     */
    private String userAgent;

    /**
     * Referer
     */
    private String referer;

    @Override
    public String toString() {
        return "Device [" +
                "platform=" + platform + ", " +
                "device=" + device + ", " +
                "version=" + version + ", " +
                "deviceNo=" + deviceNo + ", " +
                "deviceModel=" + deviceModel + ", " +
                "osVersion=" + osVersion + ", " +
                "userAgent=" + userAgent + ", " +
                "referer=" + referer + "]";
    }
}
