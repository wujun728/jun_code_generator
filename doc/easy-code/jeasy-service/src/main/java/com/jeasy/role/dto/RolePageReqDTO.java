package com.jeasy.role.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色 分页 ReqDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class RolePageReqDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 名称
     */
    @InitField(value = "", desc = "名称")
    private String name;

    /**
     * 更新起始时间
     */
    @InitField(value = "", desc = "更新起始时间")
    private String updateStartAt;

    /**
     * 更新结束时间
     */
    @InitField(value = "", desc = "更新结束时间")
    private String updateEndAt;
}
