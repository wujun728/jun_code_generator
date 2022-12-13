package com.jeasy.base.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.jeasy.common.thread.ThreadLocalKit;
import lombok.Data;

import java.io.Serializable;

/**
 * BaseEntity
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_COL_ID = "id";

    public static final String DB_COL_IS_DEL = "isDel";

    public static final String DB_COL_IS_TEST = "isTest";

    public static final String DB_COL_CREATE_AT = "createAt";

    public static final String DB_COL_CREATE_BY = "createBy";

    public static final String DB_COL_CREATE_NAME = "createName";

    public static final String DB_COL_UPDATE_AT = "updateAt";

    public static final String DB_COL_UPDATE_BY = "updateBy";

    public static final String DB_COL_UPDATE_NAME = "updateName";

    /**
     * 主键
     */
    private Long id;
    /**
     * 是否删除
     */
    @TableLogic
    private Integer isDel = 0;
    /**
     * 是否测试
     */
    private Integer isTest = 0;
    /**
     * 创建时间
     */
    private Long createAt;
    /**
     * 创建者ID
     */
    private Long createBy;
    /**
     * 创建者名称
     */
    private String createName;
    /**
     * 更新时间
     */
    private Long updateAt;
    /**
     * 更新者ID
     */
    private Long updateBy;
    /**
     * 更新者名称
     */
    private String updateName;

    public final Integer getIsTest() {
        return isTest != null ? isTest : (ThreadLocalKit.getCurrentUser() == null ? Integer.valueOf(0) : ThreadLocalKit.getCurrentUser().getIsTest());
    }

    public final void setIsTest() {
        this.isTest = ThreadLocalKit.getCurrentUser() == null ? Integer.valueOf(0) : ThreadLocalKit.getCurrentUser().getIsTest();
    }
}
