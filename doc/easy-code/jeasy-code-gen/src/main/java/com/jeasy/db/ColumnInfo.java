package com.jeasy.db;

import com.jeasy.common.str.StrKit;
import com.jeasy.util.TypeKit;
import lombok.Data;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/10/19 12:17
 */
@Data
public class ColumnInfo {

    private static final String ID = "id";

    public ColumnInfo(String name, String type, String comment) {
        this.name = name;
        this.type = type;
        this.comment = comment;
    }

    private String name;

    private String type;

    private String comment;

    public String getName() {
        return name;
    }

    public String getCamelName() {
        return StrKit.toCamelName(name);
    }

    public String getUnderLineUpperName() {
        return StrKit.toUnderlineName(name).toUpperCase();
    }

    public String getClassName() {
        return StrKit.toUpperCaseFirstOne(getCamelName());
    }

    public String getJavaType() {
        if (ID.equalsIgnoreCase(name)) {
            return "Long";
        }

        if (TypeKit.JAVA_TYPE_MAP.containsKey(type)) {
            return TypeKit.JAVA_TYPE_MAP.get(type);
        }

        return type;
    }

    public String getMyBatisType() {
        if (TypeKit.MYBATIS_TYPE_MAP.containsKey(type)) {
            return TypeKit.MYBATIS_TYPE_MAP.get(type);
        }

        return type;
    }

    public String getClassImport() {
        if (TypeKit.CLASS_IMPORT_MAP.containsKey(type)) {
            return TypeKit.CLASS_IMPORT_MAP.get(type);
        }
        return StrKit.S_EMPTY;
    }
}
