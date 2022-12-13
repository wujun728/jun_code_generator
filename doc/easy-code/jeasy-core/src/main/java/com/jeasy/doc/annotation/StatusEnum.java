package com.jeasy.doc.annotation;

/**
 * @author taomk
 * @version 1.0
 * @since 15-6-7 上午6:50
 */
public enum StatusEnum {

    /**
     * 未开始
     */
    TODO("未开始"),

    /**
     * 开发中
     */
    DOING("开发中"),

    /**
     * 已完成
     */
    DONE("已完成");

    private String statusName;

    private StatusEnum(String statusName) {
        this.statusName = statusName;
    }

    public String statusName() {
        return this.statusName;
    }
}
