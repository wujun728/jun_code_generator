package com.jeasy.user.dto;

import com.google.gson.annotations.SerializedName;
import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色 分页 ResDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class UserPageRoleResDTO implements AnnotationValidable, Serializable {

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
     * 备注
     */
    @InitField(value = "", desc = "备注")
    private String remark;

    /**
     * 更新时间
     */
    @InitField(value = "", desc = "更新时间")
    private String updateAt;

    /**
     * 更新人
     */
    @InitField(value = "", desc = "更新人")
    private String updateName;

    /**
     * 是否勾选
     */
    @InitField(value = "", desc = "是否勾选")
    @SerializedName("_checked")
    private Boolean checked;
}
