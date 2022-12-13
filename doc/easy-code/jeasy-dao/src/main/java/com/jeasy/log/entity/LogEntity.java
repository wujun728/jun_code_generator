package com.jeasy.log.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jeasy.base.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日志
 *
 * @author taomk
 * @version 1.0
 * @since 2019/06/20 17:27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("bd_log")
public class LogEntity extends BaseEntity {

    private static final long serialVersionUID = 5409185459234711691L;

    public static final String DB_TABLE_NAME = "bd_log";

    public static final String DB_COL_TABLE_NAME = "tableName";

    public static final String DB_COL_RECORD_ID = "recordId";

    public static final String DB_COL_FIELD_NAME = "fieldName";

    public static final String DB_COL_LOG_TYPE_VAL = "logTypeVal";

    public static final String DB_COL_LOG_TYPE_CODE = "logTypeCode";

    public static final String DB_COL_OPT_TYPE_VAL = "optTypeVal";

    public static final String DB_COL_OPT_TYPE_CODE = "optTypeCode";

    public static final String DB_COL_OPT_DESC = "optDesc";

    public static final String DB_COL_BEFORE_VALUE = "beforeValue";

    public static final String DB_COL_AFTER_VALUE = "afterValue";

    public static final String DB_COL_REMARK = "remark";


    /**
     * 表名称
     */
    private String tableName;

    /**
     * 记录ID
     */
    private Long recordId;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 日志类型值
     */
    private Integer logTypeVal;

    /**
     * 日志类型编码:字典
     */
    private String logTypeCode;

    /**
     * 操作类型值
     */
    private Integer optTypeVal;

    /**
     * 操作类型编码:字典
     */
    private String optTypeCode;

    /**
     * 操作类型描述
     */
    private String optDesc;

    /**
     * 操作前值
     */
    private String beforeValue;

    /**
     * 操作后值
     */
    private String afterValue;

    /**
     * 备注
     */
    private String remark;

}
