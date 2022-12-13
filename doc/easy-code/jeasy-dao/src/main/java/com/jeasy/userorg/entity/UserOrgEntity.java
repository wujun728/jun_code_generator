package com.jeasy.userorg.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeasy.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户机构
 *
 * @author taomk
 * @version 1.0
 * @since 2018/06/06 14:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("su_user_org")
public class UserOrgEntity extends BaseEntity {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_TABLE_NAME = "su_user_org";

    public static final String DB_COL_USER_ID = "userId";

    public static final String DB_COL_USER_NAME = "userName";

    public static final String DB_COL_USER_CODE = "userCode";

    public static final String DB_COL_ORG_ID = "orgId";

    public static final String DB_COL_ORG_NAME = "orgName";

    public static final String DB_COL_ORG_CODE = "orgCode";


    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户标示
     */
    private String userCode;

    /**
     * 机构ID
     */
    private Long orgId;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 机构编码
     */
    private String orgCode;

}
