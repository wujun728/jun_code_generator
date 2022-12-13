package com.jeasy.user.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户 分页 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class UserPageResDTO implements AnnotationValidable, Serializable {

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
     * 手机号
     */
    @InitField(value = "", desc = "手机号")
    private String mobile;

    /**
     * 用户状态名称
     */
    @InitField(value = "", desc = "启用")
    private String statusName;

    /**
     * 更新时间
     */
    @InitField(value = "", desc = "更新时间")
    private String updateAt;

    /**
     * 更新人
     */
    @InitField(value = "", desc = "更新人")
    private String updateName;
}
