package com.jeasy.user.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户 修改 ReqDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class UserModifyReqDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 主键
     */
    @InitField(value = "", desc = "主键")
    private Long id;

    /**
     * 名称
     */
    @InitField(value = "", desc = "名称")
    private String name;

    /**
     * 登录名
     */
    @InitField(value = "", desc = "登录名")
    private String loginName;

    /**
     * 密码
     */
    @InitField(value = "", desc = "密码")
    private String pwd;

    /**
     * 加密盐
     */
    @InitField(value = "", desc = "加密盐")
    private String salt;

    /**
     * 手机号
     */
    @InitField(value = "", desc = "手机号")
    private String mobile;

    /**
     * 用户状态编码:字典
     */
    @InitField(value = "", desc = "用户状态编码:字典")
    private String statusCode;

    /**
     * 备注
     */
    @InitField(value = "", desc = "备注")
    private String remark;
}
