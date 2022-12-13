package com.jeasy.util;

import com.google.common.collect.ImmutableMap;

/**
 * 类型工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 18:06
 */
public final class TypeKit {

    public static final ImmutableMap<String, String> JAVA_TYPE_MAP = ImmutableMap.<String, String>builder()
        .put("INT", "Integer")
        .put("BIGINT", "Long")
        .put("INTEGER", "Integer")
        .put("TINYINT", "Integer")
        .put("SMALLINT", "Integer")
        .put("MEDIUMINT", "Integer")
        .put("FLOAT", "Float")
        .put("DOUBLE", "Double")
        .put("DECIMAL", "BigDecimal")
        .put("INT UNSIGNED", "Integer")
        .put("BIGINT UNSIGNED", "Long")
        .put("INTEGER UNSIGNED", "Integer")
        .put("TINYINT UNSIGNED", "Integer")
        .put("SMALLINT UNSIGNED", "Integer")
        .put("MEDIUMINT UNSIGNED", "Integer")
        .put("FLOAT UNSIGNED", "Float")
        .put("DOUBLE UNSIGNED", "Double")
        .put("DECIMAL UNSIGNED", "BigDecimal")
        .put("BIT", "Integer")
        .put("BOOLEAN", "Integer")
        .put("BOOL", "Integer")
        .put("VARCHAR", "String")
        .put("CHAR", "String")
        .put("TEXT", "String")
        .put("MEDIUMTEXT", "String")
        .put("LONGTEXT", "String")
        .put("BLOB", "Byte[]")
        .put("MEDIUMBLOB", "Byte[]")
        .put("LONGBLOB", "Byte[]")
        .put("TINYBLOB", "Byte[]")
        .put("BINARY", "Byte[]")
        .put("VARBINARY", "Byte[]")
        .put("DATE", "Date")
        .put("TIME", "Date")
        .put("DATETIME", "Date")
        .put("TIMESTAMP", "Date")
        .put("YEAR", "Date")
        .build();


    public static final ImmutableMap<String, String> CLASS_IMPORT_MAP = ImmutableMap.<String, String>builder()
        .put("DATE", "java.util.Date")
        .put("TIME", "java.util.Date")
        .put("DATETIME", "java.util.Date")
        .put("TIMESTAMP", "java.util.Date")
        .put("YEAR", "java.util.Date")
        .put("DECIMAL", "java.math.BigDecimal")
        .build();

    public static final ImmutableMap<String, String> MYBATIS_TYPE_MAP = ImmutableMap.<String, String>builder()
        .put("INT", "INTEGER")
        .put("BIGINT", "BIGINT")
        .put("INTEGER", "INTEGER")
        .put("TINYINT", "TINYINT")
        .put("SMALLINT", "SMALLINT")
        .put("MEDIUMINT", "INTEGER")
        .put("FLOAT", "FLOAT")
        .put("DOUBLE", "DOUBLE")
        .put("DECIMAL", "DECIMAL")
        .put("INT UNSIGNED", "INTEGER")
        .put("BIGINT UNSIGNED", "BIGINT")
        .put("INTEGER UNSIGNED", "INTEGER")
        .put("TINYINT UNSIGNED", "TINYINT")
        .put("SMALLINT UNSIGNED", "SMALLINT")
        .put("MEDIUMINT UNSIGNED", "INTEGER")
        .put("FLOAT UNSIGNED", "FLOAT")
        .put("DOUBLE UNSIGNED", "DOUBLE")
        .put("DECIMAL UNSIGNED", "DECIMAL")
        .put("BIT", "TINYINT")
        .put("BOOLEAN", "BOOLEAN")
        .put("BOOL", "BOOLEAN")
        .put("VARCHAR", "VARCHAR")
        .put("CHAR", "CHAR")
        .put("TEXT", "LONGVARCHAR")
        .put("MEDIUMTEXT", "LONGVARCHAR")
        .put("LONGTEXT", "LONGVARCHAR")
        .put("BLOB", "BLOB")
        .put("MEDIUMBLOB", "BLOB")
        .put("LONGBLOB", "BLOB")
        .put("TINYBLOB", "BLOB")
        .put("BINARY", "BINARY")
        .put("VARBINARY", "VARBINARY")
        .put("DATE", "DATE")
        .put("TIME", "TIME")
        .put("DATETIME", "TIMESTAMP")
        .put("TIMESTAMP", "TIMESTAMP")
        .put("YEAR", "DATE")
        .build();
}
