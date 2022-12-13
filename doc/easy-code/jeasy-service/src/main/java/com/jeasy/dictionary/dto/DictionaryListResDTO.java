package com.jeasy.dictionary.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典 列表 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class DictionaryListResDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 字典主键
     */
    @InitField(value = "1000", desc = "字典主键")
    private Long id;

    /**
     * 字典父id
     */
    @InitField(value = "1000", desc = "字典pid")
    private Long pid;

    /**
     * 字典名称
     */
    @InitField(value = "XXXX", desc = "字典名称")
    private String name;

    /**
     * 字典编号
     */
    @InitField(value = "YHZT", desc = "字典编号")
    private String code;

    /**
     * 字典值
     */
    @InitField(value = "1001", desc = "字典值")
    private Integer value;
}
