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
public class UserMenuResourceDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 菜单ID
     */
    @InitField(value = "1000", desc = "菜单ID")
    private Long id;

    /**
     * 菜单名称
     */
    @InitField(value = "用户管理", desc = "菜单名称")
    private String name;

    /**
     * 菜单编码
     */
    @InitField(value = "YHGL", desc = "菜单编码")
    private String code;

    /**
     * 菜单图标
     */
    @InitField(value = "icon-nav", desc = "菜单图标")
    private String icon;

    /**
     * 菜单URL
     */
    @InitField(value = "", desc = "菜单URL")
    private String url;

    @InitField(desc = "子菜单")
    private List<UserMenuResourceDTO> childrens;
}
