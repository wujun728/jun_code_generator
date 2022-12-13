package com.jeasy.common.ueditor.define;

import java.util.Map;

/**
 * 处理状态接口
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public interface State {

    /**
     * isSuccess
     *
     * @return
     */
    boolean isSuccess();

    /**
     * putInfo
     *
     * @param name
     * @param val
     */
    void putInfo(String name, String val);

    /**
     * putInfo
     *
     * @param name
     * @param val
     */
    void putInfo(String name, long val);

    /**
     * toJSONString
     *
     * @return
     */
    String toJSONString();

    /**
     * toJSONObject
     *
     * @return
     */
    Map<String, Object> toJSONObject();
}
