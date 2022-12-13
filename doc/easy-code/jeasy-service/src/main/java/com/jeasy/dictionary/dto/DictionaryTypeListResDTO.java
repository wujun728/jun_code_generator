package com.jeasy.dictionary.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 字典 DTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/03/28 15:13
 */
@Data
@AllArgsConstructor
public class DictionaryTypeListResDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 字典类型名称
     */
    @InitField(value = "XXXX", desc = "字典类型名称")
    private String name;

    /**
     * 字典类型编号
     */
    @InitField(value = "YHZT", desc = "字典类型编号")
    private String code;
}
