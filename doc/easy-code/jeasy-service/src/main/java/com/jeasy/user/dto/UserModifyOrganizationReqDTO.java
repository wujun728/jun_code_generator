package com.jeasy.user.dto;

import com.jeasy.doc.annotation.InitField;
import com.jeasy.validate.AnnotationValidable;
import com.jeasy.validate.handler.ValidateNotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户 修改 ReqDTO
 *
 * @author taomk
 * @version 1.0
 * @since 2017/11/08 16:33
 */
@Data
public class UserModifyOrganizationReqDTO implements AnnotationValidable, Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    /**
     * 用户ID
     */
    @InitField(value = "", desc = "用户ID")
    @ValidateNotNull(message = "用户ID不允许为空")
    private Long userId;

    /**
     * 机构ID集合
     */
    @InitField(value = "", desc = "机构ID集合")
    private List<Long> orgIds;
}
