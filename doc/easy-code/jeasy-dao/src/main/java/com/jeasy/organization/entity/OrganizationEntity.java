package com.jeasy.organization.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeasy.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构
 *
 * @author taomk
 * @version 1.0
 * @since 2018/06/06 14:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("su_organization")
public class OrganizationEntity extends BaseEntity {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_TABLE_NAME = "su_organization";

    public static final String DB_COL_NAME = "name";

    public static final String DB_COL_CODE = "code";

    public static final String DB_COL_ADDRESS = "address";

    public static final String DB_COL_TYPE_VAL = "typeVal";

    public static final String DB_COL_TYPE_CODE = "typeCode";

    public static final String DB_COL_SORT = "sort";

    public static final String DB_COL_IS_LEAF = "isLeaf";

    public static final String DB_COL_PID = "pid";

    public static final String DB_COL_ICON = "icon";

    public static final String DB_COL_REMARK = "remark";


    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * 地址
     */
    private String address;

    /**
     * 机构类型值
     */
    private Integer typeVal;

    /**
     * 机构类型编码:字典
     */
    private String typeCode;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否叶子节点:0=否,1=是
     */
    private Integer isLeaf;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 图标
     */
    private String icon;

    /**
     * 备注/描述
     */
    private String remark;

}
