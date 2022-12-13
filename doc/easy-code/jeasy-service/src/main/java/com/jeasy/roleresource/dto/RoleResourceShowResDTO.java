package com.jeasy.roleresource.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色资源 详情 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class RoleResourceShowResDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 主键
     */
    @InitField(value = "", desc = "主键")
    private Long id;

    /**
     * 角色ID
     */
    @InitField(value = "", desc = "角色ID")
    private Long roleId;

    /**
     * 角色名称
     */
    @InitField(value = "", desc = "角色名称")
    private String roleName;

    /**
     * 角色编码
     */
    @InitField(value = "", desc = "角色编码")
    private String roleCode;

    /**
     * 资源ID
     */
    @InitField(value = "", desc = "资源ID")
    private Long resourceId;

    /**
     * 资源名称
     */
    @InitField(value = "", desc = "资源名称")
    private String resourceName;

    /**
     * 资源编码
     */
    @InitField(value = "", desc = "资源编码")
    private String resourceCode;

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
