package com.jeasy.userorg.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户机构 详情 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class UserOrgShowResDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 主键
     */
    @InitField(value = "", desc = "主键")
    private Long id;

    /**
     * 用户ID
     */
    @InitField(value = "", desc = "用户ID")
    private Long userId;

    /**
     * 用户名称
     */
    @InitField(value = "", desc = "用户名称")
    private String userName;

    /**
     * 用户标示
     */
    @InitField(value = "", desc = "用户标示")
    private String userCode;

    /**
     * 机构ID
     */
    @InitField(value = "", desc = "机构ID")
    private Long orgId;

    /**
     * 机构名称
     */
    @InitField(value = "", desc = "机构名称")
    private String orgName;

    /**
     * 机构编码
     */
    @InitField(value = "", desc = "机构编码")
    private String orgCode;

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
