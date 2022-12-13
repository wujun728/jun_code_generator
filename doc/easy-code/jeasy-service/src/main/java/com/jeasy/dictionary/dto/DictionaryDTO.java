package com.jeasy.dictionary.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典 DTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class DictionaryDTO implements AnnotationValidable, Serializable {

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
     * 编号
     */
    @InitField(value = "", desc = "编号")
    private String code;

    /**
     * 值
     */
    @InitField(value = "", desc = "值")
    private Integer value;

    /**
     * 类型
     */
    @InitField(value = "", desc = "类型")
    private String type;

    /**
     * 类型名称
     */
    @InitField(value = "", desc = "类型名称")
    private String typeName;

    /**
     * 排序
     */
    @InitField(value = "", desc = "排序")
    private Integer sort;

    /**
     * 父ID
     */
    @InitField(value = "", desc = "父ID")
    private Long pid;

    /**
     * 父编号
     */
    @InitField(value = "", desc = "父编号")
    private String pcode;

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
