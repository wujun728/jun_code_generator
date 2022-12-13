package com.jeasy.log.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 日志 详情 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Data
public class LogShowResDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 主键
     */
    @InitField(value = "", desc = "主键")
    private Long id;

    /**
     * 表名称
     */
    @InitField(value = "", desc = "表名称")
    private String tableName;

    /**
     * 记录ID
     */
    @InitField(value = "", desc = "记录ID")
    private Long recordId;

    /**
     * 字段名称
     */
    @InitField(value = "", desc = "字段名称")
    private String fieldName;

    /**
     * 日志类型值
     */
    @InitField(value = "", desc = "日志类型值")
    private Integer logTypeVal;

    /**
     * 日志类型编码:字典
     */
    @InitField(value = "", desc = "日志类型编码:字典")
    private String logTypeCode;

    /**
     * 操作类型值
     */
    @InitField(value = "", desc = "操作类型值")
    private Integer optTypeVal;

    /**
     * 操作类型编码:字典
     */
    @InitField(value = "", desc = "操作类型编码:字典")
    private String optTypeCode;

    /**
     * 操作类型描述
     */
    @InitField(value = "", desc = "操作类型描述")
    private String optDesc;

    /**
     * 操作前值
     */
    @InitField(value = "", desc = "操作前值")
    private String beforeValue;

    /**
     * 操作后值
     */
    @InitField(value = "", desc = "操作后值")
    private String afterValue;

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
