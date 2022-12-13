package com.jeasy.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeasy.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户
 *
 * @author taomk
 * @version 1.0
 * @since 2018/06/06 14:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("su_user")
public class UserEntity extends BaseEntity {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_TABLE_NAME = "su_user";

    public static final String DB_COL_NAME = "name";

    public static final String DB_COL_LOGIN_NAME = "loginName";

    public static final String DB_COL_CODE = "code";

    public static final String DB_COL_PWD = "pwd";

    public static final String DB_COL_SALT = "salt";

    public static final String DB_COL_MOBILE = "mobile";

    public static final String DB_COL_STATUS_VAL = "statusVal";

    public static final String DB_COL_STATUS_CODE = "statusCode";

    public static final String DB_COL_REMARK = "remark";


    /**
     * 名称
     */
    private String name;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 编码
     */
    private String code;

    /**
     * 密码
     */
    private String pwd;

    /**
     * 加密盐
     */
    private String salt;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 用户状态值:1000=启用,1001=停用
     */
    private Integer statusVal;

    /**
     * 用户状态编码:字典
     */
    private String statusCode;

    /**
     * 备注
     */
    private String remark;

}
