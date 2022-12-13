package com.jeasy.base.web.controller;

import com.jeasy.base.web.dto.CurrentUser;
import com.jeasy.common.date.DateKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Abstract BaseController
 *
 * @param <S> ServiceImpl
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Slf4j
public class BaseController<S> extends ControllerSupport {

    @Autowired
    protected S service;

    @InitBinder
    public void initBinder(final ServletRequestDataBinder binder) {
        /**
         * 自动转换日期类型的字段格式
         */
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
        // /**
        //  * 防止XSS攻击
        //  */
        // binder.registerCustomEditor(String.class, new StringEscapeEditor());

        /**
         * Timestamp 类型转换
         */
        binder.registerCustomEditor(Timestamp.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(final String text) {
                Date date = DateKit.parseDate(text);
                setValue(date == null ? null : new Timestamp(date.getTime()));
            }
        });
    }

    /**
     * 获取当前登录用户对象
     *
     * @return {CurrentUser}
     */
    public CurrentUser getCurrentUser() {
        return (CurrentUser) SecurityUtils.getSubject().getPrincipal();
    }

    /**
     * 获取当前登录用户id
     *
     * @return {Long}
     */
    public Long getUserId() {
        return this.getCurrentUser().getId();
    }

    /**
     * 获取当前登录用户名
     *
     * @return {String}
     */
    public String getStaffName() {
        return this.getCurrentUser().getName();
    }
}
