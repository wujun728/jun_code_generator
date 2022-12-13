package com.jeasy.dictionary.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典 修改 ReqDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class DictionaryModifyReqDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 字典id
     */
    @ValidateNotNull(message = "字典ID不允许为空")
    @InitField(value = "1000", desc = "字典id")
    private Long id;

    /**
     * 字典名称
     */
    @ValidateNotNull(message = "字典名称不允许为空")
    @InitField(value = "XXXX", desc = "字典名称")
    private String name;

    /**
     * 字典编号
     */
    @ValidateNotNull(message = "字典编号不允许为空")
    @InitField(value = "YHZT", desc = "字典编号")
    private String code;

    /**
     * 字典值
     */
    @ValidateNotNull(message = "字典值不允许为空")
    @InitField(value = "1001", desc = "字典值")
    private Integer value;

    /**
     * 字典类型
     */
    @ValidateNotNull(message = "字典类型不允许为空")
    @InitField(value = "YHZT", desc = "字典类型")
    private String type;

    /**
     * 字典类型名称
     */
    @ValidateNotNull(message = "字典类型名称不允许为空")
    @InitField(value = "XXXX", desc = "字典类型名称")
    private String typeName;

    /**
     * 父字典ID
     */
    @InitField(value = "YHZT", desc = "父字典id")
    private Long pid;

    /**
     * 父字典编号
     */
    @InitField(value = "YHZT", desc = "父字典编号")
    private String pcode;
}
