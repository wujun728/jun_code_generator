package com.jeasy.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeasy.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单
 *
 * @author taomk
 * @version 1.0
 * @since 2018/06/06 14:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("su_resource")
public class ResourceEntity extends BaseEntity {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_TABLE_NAME = "su_resource";

    public static final String DB_COL_NAME = "name";

    public static final String DB_COL_CODE = "code";

    public static final String DB_COL_URL = "url";

    public static final String DB_COL_ICON = "icon";

    public static final String DB_COL_REMARK = "remark";

    public static final String DB_COL_PID = "pid";

    public static final String DB_COL_SORT = "sort";

    public static final String DB_COL_IS_MENU = "isMenu";

    public static final String DB_COL_IS_LEAF = "isLeaf";


    /**
     * 名称
     */
    private String name;

    /**
     * 编码
     */
    private String code;

    /**
     * URL
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 备注/描述
     */
    private String remark;

    /**
     * 父ID
     */
    private Long pid;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否菜单:0=否,1=是
     */
    private Integer isMenu;

    /**
     * 是否叶子节点:0=否,1=是
     */
    private Integer isLeaf;

}
