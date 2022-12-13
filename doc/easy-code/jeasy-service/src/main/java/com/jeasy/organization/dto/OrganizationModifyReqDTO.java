package com.jeasy.organization.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 机构 修改 ReqDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class OrganizationModifyReqDTO implements AnnotationValidable, Serializable {

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
     * 备注/描述
     */
    @InitField(value = "", desc = "备注/描述")
    private String remark;
}
