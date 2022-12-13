package com.jeasy.user.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户 详情 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class UserShowResDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 主键
     */
    @InitField(value = "", desc = "主键")
    private Long id;

    /**
     * 用户名称
     */
    @InitField(value = "", desc = "用户名称")
    private String name;

    /**
     * 登录名称
     */
    @InitField(value = "", desc = "登录名称")
    private String loginName;

    /**
     * 手机号码
     */
    @InitField(value = "", desc = "手机号码")
    private String mobile;

    /**
     * 用户状态值:1000=启用,1001=停用
     */
    @InitField(value = "", desc = "用户状态值:1000=启用,1001=停用")
    private Integer statusVal;

    /**
     * 用户状态编码
     */
    @InitField(value = "", desc = "用户状态编码")
    private String statusCode;

    /**
     * 用户状态
     */
    @InitField(value = "", desc = "用户状态")
    private String statusName;

    /**
     * 加密盐
     */
    @InitField(value = "", desc = "加密盐")
    private String salt;

    /**
     * 备注
     */
    @InitField(value = "", desc = "备注")
    private String remark;

    /**
     * 角色名称
     */
    @InitField(value = "", desc = "角色名称")
    private String roleNames;

    /**
     * 机构名称
     */
    @InitField(value = "", desc = "机构名称")
    private String orgNames;
}
