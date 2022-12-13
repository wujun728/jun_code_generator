package com.jeasy.roleresource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeasy.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色资源
 *
 * @author taomk
 * @version 1.0
 * @since 2018/06/06 14:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("su_role_resource")
public class RoleResourceEntity extends BaseEntity {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_TABLE_NAME = "su_role_resource";

    public static final String DB_COL_ROLE_ID = "roleId";

    public static final String DB_COL_ROLE_NAME = "roleName";

    public static final String DB_COL_ROLE_CODE = "roleCode";

    public static final String DB_COL_RESOURCE_ID = "resourceId";

    public static final String DB_COL_RESOURCE_NAME = "resourceName";

    public static final String DB_COL_RESOURCE_CODE = "resourceCode";


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

    /**
     * 资源ID
     */
    private Long resourceId;

    /**
     * 资源名称
     */
    private String resourceName;

    /**
     * 资源编码
     */
    private String resourceCode;

}
