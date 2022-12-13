package com.jeasy.organization.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 机构 DTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class OrganizationDTO implements AnnotationValidable, Serializable {

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
     * 编码
     */
    @InitField(value = "", desc = "编码")
    private String code;

    /**
     * 地址
     */
    @InitField(value = "", desc = "地址")
    private String address;

    /**
     * 机构类型值
     */
    @InitField(value = "", desc = "机构类型值")
    private Integer typeVal;

    /**
     * 机构类型编码:字典
     */
    @InitField(value = "", desc = "机构类型编码:字典")
    private String typeCode;

    /**
     * 排序
     */
    @InitField(value = "", desc = "排序")
    private Integer sort;

    /**
     * 是否叶子节点:0=否,1=是
     */
    @InitField(value = "", desc = "是否叶子节点:0=否,1=是")
    private Integer isLeaf;

    /**
     * 父ID
     */
    @InitField(value = "", desc = "父ID")
    private Long pid;

    /**
     * 图标
     */
    @InitField(value = "", desc = "图标")
    private String icon;

    /**
     * 备注/描述
     */
    @InitField(value = "", desc = "备注/描述")
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
