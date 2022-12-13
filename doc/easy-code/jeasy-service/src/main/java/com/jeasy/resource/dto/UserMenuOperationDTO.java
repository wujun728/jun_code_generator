package com.jeasy.resource.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单 DTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/03/28 15:13
 */
@Data
public class UserMenuOperationDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 操作ID
     */
    @InitField(value = "1010", desc = "操作ID")
    private Long id;

    /**
     * 操作名称
     */
    @InitField(value = "添加", desc = "操作名称")
    private String name;

    /**
     * 操作编码
     */
    @InitField(value = "YHGL_RYGL_ADD", desc = "操作编码")
    private String code;

    /**
     * 操作图标
     */
    @InitField(value = "icon-nav", desc = "操作图标")
    private String icon;
}
