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
public class GenTableColumn implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "column_id", type = IdType.AUTO)
    private Integer columnId;

    private String tableId;

    private String columnName;

    private String columnComment;

    private String columnType;

    private String javaType;

    private String javaField;

    private String isPk;

    private String isIncrement;

    private String isRequired;

    private String isInsert;

    private String isEdit;

    private String isList;

    private String isQuery;

    private String queryType;

    private String htmlType;

    private String dictType;

    private Integer sort;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;


}
