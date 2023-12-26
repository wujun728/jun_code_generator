package com.jun.plugin.book.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wujun
 * @since 2023-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GenTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "table_id", type = IdType.AUTO)
    private Integer tableId;

    private Integer dataSourceId;

    private String tableName;

    private String tableComment;

    private String subTableName;

    private String subTableFkName;

    private String className;

    private String tplCategory;

    private String packageName;

    private String moduleName;

    private String businessName;

    private String functionName;

    private String functionAuthor;

    private String genType;

    private String genPath;

    private String options;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String remark;


}
