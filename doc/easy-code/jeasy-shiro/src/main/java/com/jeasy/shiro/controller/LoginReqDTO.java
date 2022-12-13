package com.jeasy.shiro.controller;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录 DTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/03/16 15:36
 */
@Data
public class LoginReqDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 用户名
     */
    @InitField(value = "admin", desc = "用户名")
    @ValidateNotNull(message = "请输入用户名")
    private String username;

    /**
     * 密码
     */
    @InitField(value = "123456", desc = "密码")
    @ValidateNotNull(message = "请输入密码")
    private String password;

    /**
     * 验证码
     */
    @InitField(value = "scfd", desc = "验证码")
    @ValidateNotNull(message = "请输入验证码")
    private String captcha;

    /**
     * 是否记住
     */
    @InitField(value = "1", desc = "是否记住")
    private Integer rememberMe;
}
