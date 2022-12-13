package com.jeasy.db;

import com.jeasy.common.str.StrKit;
import lombok.Data;

import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/10/19 12:17
 */
@Data
public class TableInfo {

    public TableInfo(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    private String name;

    private String comment;

    private List<ColumnInfo> columns;

    public String getName() {
        return name;
    }

    public String getCamelName() {
        return StrKit.toCamelName(name.substring(3));
    }

    public String getUpperCamelName() {
        return getCamelName().toUpperCase();
    }
    public String getLowerCamelName() {
        return getCamelName().toLowerCase();
    }

    public String getClassName() {
        return StrKit.toUpperCaseFirstOne(getCamelName());
    }

    public String getInsertView() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo column : columns) {
            if (StrKit.equalsIgnoreCase("id", column.getName())) {
                continue;
            }
            sb.append("`").append(column.getName()).append("`").append(",");
        }
        return sb.substring(0, sb.lastIndexOf(","));
    }

    public String getInsertValue() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo column : columns) {
            if (StrKit.equalsIgnoreCase("id", column.getName())) {
                continue;
            }
            sb.append(":1.").append(column.getCamelName()).append(",");
        }
        return sb.substring(0, sb.lastIndexOf(","));
    }

    public String getInsertSelectiveView() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo column : columns) {
            if (StrKit.equalsIgnoreCase("id", (column.getName()))) {
                continue;
            }
            sb.append(" #if(:1.").append(column.getCamelName()).append(" != null){")
                .append(column.getName()).append(",")
                .append("}");
        }
        return sb.toString();
    }

    public String getInsertSelectiveValue() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo column : columns) {
            if (StrKit.equalsIgnoreCase("id", (column.getName()))) {
                continue;
            }
            sb.append(" #if(:1.").append(column.getCamelName()).append(" != null){")
                .append(":1.").append(column.getCamelName()).append(",")
                .append("}");
        }
        return sb.toString();
    }

    public String getUpdateValue() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo column : columns) {
            if (StrKit.equalsIgnoreCase("id", column.getName())) {
                continue;
            }
            sb.append(column.getName()).append("=:1.").append(column.getCamelName()).append(",");
        }
        return sb.substring(0, sb.lastIndexOf(","));
    }

    public String getUpdateSelectiveValue() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo column : columns) {
            if (StrKit.equalsIgnoreCase("id", column.getName())) {
                continue;
            }
            sb.append(" #if(:1.").append(column.getCamelName()).append(" != null){")
                .append(column.getName()).append("=:1.").append(column.getCamelName()).append(",")
                .append("}");
        }
        return sb.toString();
    }

    public String getSelectView() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo column : columns) {
            sb.append("`").append(column.getName()).append("`").append(",");
        }
        return sb.substring(0, sb.lastIndexOf(","));
    }

    public String getQueryParams() {
        StringBuilder sb = new StringBuilder();
        for (ColumnInfo column : columns) {
            if (StrKit.equalsIgnoreCase("BigDecimal", column.getJavaType()) ||
                StrKit.equalsIgnoreCase("String", column.getJavaType()) ||
                    StrKit.equalsIgnoreCase("byte[]", column.getJavaType()) ||
                        StrKit.equalsIgnoreCase("Date", column.getJavaType())) {
                sb.append(" #if(:1.").append(column.getCamelName()).append(" != null){")
                    .append(" AND ").append(column.getName()).append("=:1.").append(column.getCamelName())
                    .append("}");
            } else {
                sb.append(" #if(:1.").append(column.getCamelName()).append(" > 0){")
                    .append(" AND ").append(column.getName()).append("=:1.").append(column.getCamelName())
                    .append("}");
            }
        }
        return sb.toString();
    }
}
