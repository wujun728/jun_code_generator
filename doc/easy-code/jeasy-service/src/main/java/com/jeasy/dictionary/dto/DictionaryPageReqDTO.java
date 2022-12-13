package com.jeasy.dictionary.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.handler.ValidateInt;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典 分页 ReqDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class DictionaryPageReqDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 字典名称
     */
    @InitField(value = "XXXX", desc = "字典名称")
    private String name;

    /**
     * 字典编号
     */
    @InitField(value = "XXXX", desc = "字典编号")
    private String code;

    /**
     * 字典值
     */
    @ValidateInt(message = "字典值为数字")
    @InitField(value = "1000", desc = "字典值")
    private Integer value;

    /**
     * 父字典名称
     */
    @InitField(value = "XXXX", desc = "父字典名称")
    private String pname;

    /**
     * 父字典编号
     */
    @InitField(value = "XXXX", desc = "父字典编号")
    private String pcode;

    /**
     * 父字典值
     */
    @ValidateInt(message = "父字典值为数字")
    @InitField(value = "1000", desc = "父字典值")
    private Integer pvalue;

    /**
     * 字典类型
     */
    @InitField(value = "YHZT", desc = "字典类型")
    private String type;

    /**
     * 更新起始时间
     */
    @InitField(value = "2017-10-12 12:00:00", desc = "更新起始时间")
    private String updateStartAt;

    /**
     * 更新结束时间
     */
    @InitField(value = "2017-10-12 13:00:00", desc = "更新结束时间")
    private String updateEndAt;
}
