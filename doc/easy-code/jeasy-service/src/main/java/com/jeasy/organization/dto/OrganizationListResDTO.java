package com.jeasy.organization.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 机构 列表 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class OrganizationListResDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 主键
     */
    @InitField(value = "", desc = "主键")
    private Long id;

    /**
     * 机构名称
     */
    @InitField(value = "", desc = "机构名称")
    private String title;

    /**
     * 是否展开
     */
    @InitField(value = "", desc = "是否展开")
    private Boolean expand;

    /**
     * 是否选中
     */
    @InitField(value = "", desc = "是否选中")
    private Boolean selected;

    /**
     * 是否勾选
     */
    @InitField(value = "", desc = "是否勾选")
    private Boolean checked;

    /**
     * 是否不可用
     */
    @InitField(value = "", desc = "是否不可用")
    private Boolean disabled;

    /**
     * 父ID
     */
    @InitField(value = "", desc = "父ID")
    private Long pid;

    /**
     * 子机构
     */
    @InitField(value = "", desc = "子机构")
    private List<OrganizationListResDTO> children;
}
