package com.jeasy.common.ueditor.define;

import com.google.common.collect.ImmutableMap;

/**
 * 定义请求action类型
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public final class ActionMap {
    /**
     * 获取配置请求
     */
    public static final int CONFIG = 0;
    public static final int UPLOAD_IMAGE = 1;
    public static final int UPLOAD_SCRAWL = 2;
    public static final int UPLOAD_VIDEO = 3;
    public static final int UPLOAD_FILE = 4;
    public static final int CATCH_IMAGE = 5;
    public static final int LIST_FILE = 6;
    public static final int LIST_IMAGE = 7;

    public static final ImmutableMap<String, Integer> ACTION_MAP = ImmutableMap.<String, Integer>builder()
        .put("config", ActionMap.CONFIG)
        .put("uploadimage", ActionMap.UPLOAD_IMAGE)
        .put("uploadscrawl", ActionMap.UPLOAD_SCRAWL)
        .put("uploadvideo", ActionMap.UPLOAD_VIDEO)
        .put("uploadfile", ActionMap.UPLOAD_FILE)
        .put("catchimage", ActionMap.CATCH_IMAGE)
        .put("listfile", ActionMap.LIST_FILE)
        .put("listimage", ActionMap.LIST_IMAGE)
        .build();

    private ActionMap() {
    }

    public static int getType(final String key) {
        return ActionMap.ACTION_MAP.get(key);
    }
}
