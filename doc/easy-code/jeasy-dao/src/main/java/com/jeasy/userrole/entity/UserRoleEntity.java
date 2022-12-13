package com.jeasy.userrole.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeasy.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户角色
 *
 * @author taomk
 * @version 1.0
 * @since 2018/06/06 14:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("su_user_role")
public class UserRoleEntity extends BaseEntity {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_TABLE_NAME = "su_user_role";

    public static final String DB_COL_USER_ID = "userId";

    public static final String DB_COL_USER_NAME = "userName";

    public static final String DB_COL_USER_CODE = "userCode";

    public static final String DB_COL_ROLE_ID = "roleId";

    public static final String DB_COL_ROLE_NAME = "roleName";

    public static final String DB_COL_ROLE_CODE = "roleCode";


    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户编码
     */
    private String userCode;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    private String roleCode;

}
