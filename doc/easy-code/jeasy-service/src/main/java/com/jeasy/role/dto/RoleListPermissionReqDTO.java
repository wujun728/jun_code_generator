package com.jeasy.role.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户角色 分页 ReqDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class RoleListPermissionReqDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 角色ID
     */
    @InitField(value = "", desc = "角色ID")
    @ValidateNotNull(message = "角色ID不允许为空")
    private Long roleId;
}
