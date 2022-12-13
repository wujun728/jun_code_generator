package com.jeasy.user.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户 列表 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class UserListResDTO implements AnnotationValidable, Serializable {

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
     * 编码
     */
    @InitField(value = "", desc = "编码")
    private String code;

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
     * 用户状态值:1000=启用,1001=停用
     */
    @InitField(value = "", desc = "用户状态值:1000=启用,1001=停用")
    private Integer statusVal;

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

    /**
     * 创建时间
     */
    @InitField(value = "", desc = "创建时间")
    private Long createAt;

    /**
     * 创建人ID
     */
    @InitField(value = "", desc = "创建人ID")
    private Long createBy;

    /**
     * 创建人名称
     */
    @InitField(value = "", desc = "创建人名称")
    private String createName;

    /**
     * 更新时间
     */
    @InitField(value = "", desc = "更新时间")
    private Long updateAt;

    /**
     * 更新人ID
     */
    @InitField(value = "", desc = "更新人ID")
    private Long updateBy;

    /**
     * 更新人名称
     */
    @InitField(value = "", desc = "更新人名称")
    private String updateName;

    /**
     * 是否删除
     */
    @InitField(value = "", desc = "是否删除")
    private Integer isDel;

    /**
     * 是否测试
     */
    @InitField(value = "", desc = "是否测试")
    private Integer isTest;

}
