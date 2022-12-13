package com.jeasy.user.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户 分页 ReqDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class UserPageReqDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

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
     * 用户状态编码:字典
     */
    @InitField(value = "", desc = "用户状态编码:字典")
    private String statusCode;

    /**
     * 更新起始时间
     */
    @InitField(value = "", desc = "更新起始时间")
    private String updateStartAt;

    /**
     * 更新结束时间
     */
    @InitField(value = "", desc = "更新结束时间")
    private String updateEndAt;
}
