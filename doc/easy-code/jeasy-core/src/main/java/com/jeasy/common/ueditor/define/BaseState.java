package com.jeasy.common.ueditor.define;

import com.jeasy.common.json.JsonKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.ueditor.Encoder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class BaseState implements State {

    private boolean state = false;
    private String info = null;

    private Map<String, Object> infoMap = new HashMap<String, Object>();

    public BaseState() {
        this.state = true;
    }

    public BaseState(boolean state) {
        this.setState(state);
    }

    public BaseState(boolean state, String info) {
        this.setState(state);
        this.info = info;
    }

    public BaseState(boolean state, int infoCode) {
        this.setState(state);
        this.info = AppInfo.getStateInfo(infoCode);
    }

    @Override
    public boolean isSuccess() {
        return this.state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setInfo(int infoCode) {
        this.info = AppInfo.getStateInfo(infoCode);
    }

    @Override
    public String toJSONString() {
        this.toJSONObject();
        return Encoder.toUnicode(JsonKit.toJson(this.infoMap));
    }

    @Override
    public void putInfo(String name, String val) {
        this.infoMap.put(name, val);
    }

    @Override
    public void putInfo(String name, long val) {
        this.putInfo(name, val + StrKit.S_EMPTY);
    }

    @Override
    public Map<String, Object> toJSONObject() {
        String stateVal = this.isSuccess() ? AppInfo.getStateInfo(AppInfo.SUCCESS) : this.info;
        this.infoMap.put("state", stateVal);
        return infoMap;
    }

}
