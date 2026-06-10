package io.github.wujun728.admin.core.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.wujun728.admin.core.exception.CodeGenerateException;
import io.github.wujun728.admin.core.model.ClassInfo;
import io.github.wujun728.admin.core.model.FieldInfo;
import io.github.wujun728.admin.core.model.NonCaseString;
import io.github.wujun728.admin.core.model.ParamInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 表格解析Util
 */
public class TableParseUtil {

    /**
     * 解析DDL SQL生成类信息
     */
    public static ClassInfo processTableIntoClassInfo(ParamInfo paramInfo) {
        return processTableIntoClassInfo(paramInfo.getTableSql());
    }

    /**
     * 解析DDL SQL生成类信息（兼容旧接口）
     */
    public static ClassInfo processTableIntoClassInfo(String tableSql) {
        NonCaseString tableSqlNcs = NonCaseString.of(tableSql);

        if (tableSqlNcs == null || tableSqlNcs.trim().length() == 0) {
            throw new CodeGenerateException("Table structure can not be empty. 表结构不能为空。");
        }

        tableSqlNcs = tableSqlNcs.trim()
                .replaceAll("'", "`")
                .replaceAll("\"", "`")
                .replaceAll("，", ",");
        tableSqlNcs = tableSqlNcs.trim().replaceAll("\\\\n`", "").replaceAll("\\+", "")
                .replaceAll("``", "`").replaceAll("\\\\", "");

        // table Name
        String tableName = null;
        if (tableSqlNcs.contains("TABLE") && tableSqlNcs.contains("(")) {
            tableName = tableSqlNcs.substring(tableSqlNcs.indexOf("TABLE") + 5, tableSqlNcs.indexOf("(")).get();
        } else if (tableSqlNcs.contains("table") && tableSqlNcs.contains("(")) {
            tableName = tableSqlNcs.substring(tableSqlNcs.indexOf("table") + 5, tableSqlNcs.indexOf("(")).get();
        } else {
            throw new CodeGenerateException("Table structure anomaly.");
        }

        if (tableName.contains("if not exists")) {
            tableName = tableName.replaceAll("if not exists", "");
        }

        if (tableName.contains("`")) {
            tableName = tableName.substring(tableName.indexOf("`") + 1, tableName.lastIndexOf("`"));
        } else {
            tableName = tableName.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
        }

        if (tableName.contains("`.`")) {
            tableName = tableName.substring(tableName.indexOf("`.`") + 3);
        } else if (tableName.contains(".")) {
            tableName = tableName.substring(tableName.indexOf(".") + 1);
        }

        String originTableName = tableName;

        // class Name
        String className = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName));
        if (className.contains("_")) {
            className = className.replaceAll("_", "");
        }

        // class Comment
        String classComment = "";
        if (tableSqlNcs.contains("COMMENT=") || tableSqlNcs.contains("comment=")) {
            tableSql = tableSqlNcs.get();
            String classCommentTmp = tableSql.substring(tableSql.lastIndexOf("COMMENT=") > -1
                    ? tableSql.lastIndexOf("COMMENT=") + 8 : tableSql.lastIndexOf("comment=") + 8).trim();
            if (classCommentTmp.contains("'") || classCommentTmp.indexOf("'") != classCommentTmp.lastIndexOf("'")) {
                classCommentTmp = classCommentTmp.substring(classCommentTmp.indexOf("'") + 1, classCommentTmp.lastIndexOf("'"));
            }
            if (classCommentTmp != null && classCommentTmp.trim().length() > 0) {
                classComment = classCommentTmp;
            }
        }

        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        tableSql = tableSqlNcs.get();
        String fieldListTmp = tableSql.substring(tableSql.indexOf("(") + 1, tableSql.lastIndexOf(")"));

        // replace "," by "，" in comment (MySQL syntax)
        Matcher matcher = Pattern.compile("\\ COMMENT '(.*?)\\'").matcher(fieldListTmp);
        while (matcher.find()) {
            String commentTmp = matcher.group();
            commentTmp = commentTmp.replaceAll("\\ COMMENT '|\\'", "");
            if (commentTmp.contains(",")) {
                String commentTmpFinal = commentTmp.replaceAll(",", "，");
                fieldListTmp = fieldListTmp.replace(commentTmp, commentTmpFinal);
            }
        }

        // remove invalid data
        for (Pattern pattern : Arrays.asList(
                Pattern.compile("[\\s]*PRIMARY KEY .*(\\),|\\))"),
                Pattern.compile("[\\s]*UNIQUE KEY .*(\\),|\\))"),
                Pattern.compile("[\\s]*KEY .*(\\),|\\))"))) {
            Matcher patternMatcher = pattern.matcher(fieldListTmp);
            while (patternMatcher.find()) {
                fieldListTmp = fieldListTmp.replace(patternMatcher.group(), "");
            }
        }

        String[] fieldLineList = fieldListTmp.split(",");
        if (fieldLineList.length > 0) {
            for (String columnLine : fieldLineList) {
                columnLine = columnLine.trim();
                if (columnLine.startsWith("`")) {

                    // column Name
                    columnLine = columnLine.substring(1);
                    String columnName = columnLine.substring(0, columnLine.indexOf("`"));

                    // field Name
                    String fieldName = StringUtils.lowerCaseFirst(StringUtils.underlineToCamelCase(columnName));
                    if (fieldName.contains("_")) {
                        fieldName = fieldName.replaceAll("_", "");
                    }

                    // field class
                    columnLine = columnLine.substring(columnLine.indexOf("`") + 1).trim();
                    String fieldClass = Object.class.getSimpleName();
                    if (columnLine.startsWith("int") || columnLine.startsWith("tinyint") || columnLine.startsWith("smallint")) {
                        fieldClass = Integer.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("bigint")) {
                        fieldClass = Long.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("float")) {
                        fieldClass = Float.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("double")) {
                        fieldClass = Double.TYPE.getSimpleName();
                    } else if (columnLine.startsWith("datetime") || columnLine.startsWith("timestamp")) {
                        fieldClass = java.util.Date.class.getSimpleName();
                    } else if (columnLine.startsWith("varchar") || columnLine.startsWith("text") || columnLine.startsWith("char")) {
                        fieldClass = String.class.getSimpleName();
                    } else if (columnLine.startsWith("decimal")) {
                        fieldClass = java.math.BigDecimal.class.getSimpleName();
                    }

                    // field comment
                    String fieldComment = "";
                    if (columnLine.contains("COMMENT")) {
                        String commentTmp = columnLine.substring(columnLine.indexOf("COMMENT") + 7).trim();
                        if (commentTmp.contains("'") || commentTmp.indexOf("'") != commentTmp.lastIndexOf("'")) {
                            commentTmp = commentTmp.substring(commentTmp.indexOf("'") + 1, commentTmp.lastIndexOf("'"));
                        }
                        fieldComment = commentTmp;
                    }

                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setColumnName(columnName);
                    fieldInfo.setFieldName(fieldName);
                    fieldInfo.setFieldClass(fieldClass);
                    fieldInfo.setFieldComment(fieldComment);

                    fieldList.add(fieldInfo);
                }
            }
        }

        if (fieldList.size() < 1) {
            throw new CodeGenerateException("Table structure anomaly.");
        }

        ClassInfo codeJavaInfo = new ClassInfo();
        codeJavaInfo.setTableName(tableName);
        codeJavaInfo.setOriginTableName(originTableName);
        codeJavaInfo.setClassName(className);
        codeJavaInfo.setClassComment(classComment);
        codeJavaInfo.setFieldList(fieldList);

        return codeJavaInfo;
    }

    /**
     * 解析DDL SQL生成类信息（带ParamInfo参数）
     */
    public static ClassInfo processTableIntoClassInfo(ParamInfo paramInfo, boolean useAdvancedParser) {
        if (!useAdvancedParser) {
            return processTableIntoClassInfo(paramInfo.getTableSql());
        }

        NonCaseString tableSql = NonCaseString.of(paramInfo.getTableSql());
        String nameCaseType = MapUtil.getString(paramInfo.getOptions(), "nameCaseType");

        if (tableSql == null || tableSql.trim().length() == 0) {
            throw new CodeGenerateException("Table structure can not be empty. 表结构不能为空。");
        }

        tableSql = tableSql.trim()
                .replaceAll("'", "`")
                .replaceAll("\"", "`")
                .replaceAll("，", ",");
        tableSql = tableSql.trim().replaceAll("\\\\n`", "").replaceAll("\\+", "")
                .replaceAll("``", "`").replaceAll("\\\\", "");

        // table Name
        String tableName = null;
        int tableKwIx = tableSql.indexOf("TABLE");
        if (tableKwIx > -1 && tableSql.contains("(")) {
            tableName = tableSql.substring(tableKwIx + 5, tableSql.indexOf("(")).get();
        } else {
            throw new CodeGenerateException("Table structure incorrect.表结构不正确。");
        }

        if (tableName.contains("if not exists")) {
            tableName = tableName.replaceAll("if not exists", "");
        }

        if (tableName.contains("`")) {
            tableName = tableName.substring(tableName.indexOf("`") + 1, tableName.lastIndexOf("`"));
        } else {
            tableName = tableName.replaceAll(" ", "").replaceAll("\n", "").replaceAll("\t", "");
        }

        if (tableName.contains("`.`")) {
            tableName = tableName.substring(tableName.indexOf("`.`") + 3);
        } else if (tableName.contains(".")) {
            tableName = tableName.substring(tableName.indexOf(".") + 1);
        }
        String originTableName = tableName;

        String className = StringUtils.upperCaseFirst(StringUtils.underlineToCamelCase(tableName));
        if (className.contains("_")) {
            className = className.replaceAll("_", "");
        }

        // class Comment
        String classComment = null;
        if (tableSql.containsAny("comment=", "comment on table")) {
            String sql = tableSql.get();
            int ix = tableSql.lastIndexOf("comment=");
            String classCommentTmp = (ix > -1) ?
                    sql.substring(ix + 8).trim() :
                    sql.substring(sql.lastIndexOf("comment on table") + 17).trim();
            if (classCommentTmp.contains("`")) {
                classCommentTmp = classCommentTmp.substring(classCommentTmp.indexOf("`") + 1);
                classCommentTmp = classCommentTmp.substring(0, classCommentTmp.indexOf("`"));
                classComment = classCommentTmp;
            } else {
                classComment = className;
            }
        } else {
            classComment = tableName;
        }

        if (classComment != null) {
            classComment = classComment.replaceAll(";", "");
        }

        // field List
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        String sql = tableSql.get();
        String fieldListTmp = sql.substring(sql.indexOf("(") + 1, sql.lastIndexOf(")"));

        // handle comments with commas
        String commentPattenStr1 = "comment `(.*?)\\`";
        Matcher matcher1 = Pattern.compile(commentPattenStr1).matcher(fieldListTmp);
        while (matcher1.find()) {
            String commentTmp = matcher1.group();
            if (commentTmp.contains(",")) {
                String commentTmpFinal = commentTmp.replaceAll(",", "，");
                fieldListTmp = fieldListTmp.replace(matcher1.group(), commentTmpFinal);
            }
        }

        String[] fieldLineList = fieldListTmp.split(",");
        if (fieldLineList.length > 0) {
            int i = 0;
            for (String columnLine0 : fieldLineList) {
                NonCaseString columnLine = NonCaseString.of(columnLine0);
                i++;
                columnLine = columnLine.replaceAll("\n", "").replaceAll("\t", "").trim();

                boolean notSpecialFlag = isNotSpecialColumnLine(columnLine, i);
                if (notSpecialFlag) {
                    if (columnLine.length() < 5) {
                        continue;
                    }
                    String columnName = "";
                    columnLine = columnLine.replaceAll("`", " ").replaceAll("\"", " ")
                            .replaceAll("'", "").replaceAll("  ", " ").trim();
                    try {
                        columnName = columnLine.substring(0, columnLine.indexOf(" ")).get();
                    } catch (StringIndexOutOfBoundsException e) {
                        System.out.println("err happened: " + columnLine);
                        throw e;
                    }

                    String fieldName;
                    if (ParamInfo.NAME_CASE_TYPE.CAMEL_CASE.equals(nameCaseType)) {
                        fieldName = StringUtils.toLowerCamel(columnName);
                    } else if (ParamInfo.NAME_CASE_TYPE.UNDER_SCORE_CASE.equals(nameCaseType)) {
                        fieldName = StringUtils.toUnderline(columnName, false);
                    } else if (ParamInfo.NAME_CASE_TYPE.UPPER_UNDER_SCORE_CASE.equals(nameCaseType)) {
                        fieldName = StringUtils.toUnderline(columnName.toUpperCase(), true);
                    } else {
                        fieldName = columnName;
                    }

                    String colStr = columnLine.get();
                    String mysqlType = colStr.split("\\s+")[1];
                    if (mysqlType.contains("(")) {
                        mysqlType = mysqlType.substring(0, mysqlType.indexOf("("));
                    }

                    String swaggerClass = "string";
                    if (mysqlJavaTypeUtil.getMysqlSwaggerTypeMap().containsKey(mysqlType)) {
                        swaggerClass = mysqlJavaTypeUtil.getMysqlSwaggerTypeMap().get(mysqlType);
                    }

                    String fieldClass = "String";
                    if (mysqlJavaTypeUtil.getMysqlJavaTypeMap().containsKey(mysqlType)) {
                        fieldClass = mysqlJavaTypeUtil.getMysqlJavaTypeMap().get(mysqlType);
                    }

                    String fieldComment = null;
                    if (sql.contains("comment on column") && (sql.contains("." + columnName + " is ") || sql.contains(".`" + columnName + "` is"))) {
                        sql = sql.replaceAll(".`" + columnName + "` is", "." + columnName + " is");
                        Matcher columnCommentMatcher = Pattern.compile("\\." + columnName + " is `").matcher(sql);
                        fieldComment = columnName;
                        while (columnCommentMatcher.find()) {
                            String columnCommentTmp = columnCommentMatcher.group();
                            fieldComment = sql.substring(sql.indexOf(columnCommentTmp) + columnCommentTmp.length()).trim();
                            fieldComment = fieldComment.substring(0, fieldComment.indexOf("`")).trim();
                        }
                    } else if (columnLine.contains(" comment")) {
                        String commentTmp = colStr.substring(colStr.lastIndexOf("comment") + 7).trim();
                        if (commentTmp.contains("`") || commentTmp.indexOf("`") != commentTmp.lastIndexOf("`")) {
                            commentTmp = commentTmp.substring(commentTmp.indexOf("`") + 1, commentTmp.lastIndexOf("`"));
                        }
                        if (commentTmp.contains(")")) {
                            commentTmp = commentTmp.substring(0, commentTmp.lastIndexOf(")") + 1);
                        }
                        fieldComment = commentTmp;
                    } else {
                        fieldComment = columnName;
                    }

                    FieldInfo fieldInfo = new FieldInfo();
                    fieldInfo.setColumnName(columnName);
                    fieldInfo.setFieldName(fieldName);
                    fieldInfo.setFieldClass(fieldClass);
                    fieldInfo.setSwaggerClass(swaggerClass);
                    fieldInfo.setFieldComment(fieldComment);

                    fieldList.add(fieldInfo);
                }
            }
        }

        if (fieldList.size() < 1) {
            throw new CodeGenerateException("表结构分析失败，请检查语句或者提交issue给我");
        }

        ClassInfo codeJavaInfo = new ClassInfo();
        codeJavaInfo.setTableName(tableName);
        codeJavaInfo.setClassName(className);
        codeJavaInfo.setClassComment(classComment);
        codeJavaInfo.setFieldList(fieldList);
        codeJavaInfo.setOriginTableName(originTableName);

        return codeJavaInfo;
    }

    private static boolean isNotSpecialColumnLine(NonCaseString columnLine, int lineSeq) {
        return (
                !columnLine.containsAny(
                        "key ",
                        "constraint",
                        " using ",
                        "unique ",
                        "fulltext ",
                        "index ",
                        "pctincrease",
                        "buffer_pool",
                        "tablespace"
                )
                && !(columnLine.contains("primary ") && columnLine.get().indexOf("storage") + 3 > columnLine.get().indexOf("("))
                && !(columnLine.contains("primary ") && lineSeq > 3)
        );
    }

    /**
     * 解析JSON生成类信息
     */
    public static ClassInfo processJsonToClassInfo(ParamInfo paramInfo) {
        ClassInfo codeJavaInfo = new ClassInfo();
        codeJavaInfo.setTableName("JsonDto");
        codeJavaInfo.setClassName("JsonDto");
        codeJavaInfo.setClassComment("JsonDto");

        if (paramInfo.getTableSql().trim().startsWith("\"")) {
            paramInfo.setTableSql("{" + paramInfo.getTableSql());
        }
        if (JSON.isValid(paramInfo.getTableSql())) {
            if (paramInfo.getTableSql().trim().startsWith("{")) {
                JSONObject jsonObject = JSONObject.parseObject(paramInfo.getTableSql().trim());
                codeJavaInfo.setFieldList(processJsonObjectToFieldList(jsonObject));
            } else if (paramInfo.getTableSql().trim().startsWith("[")) {
                JSONArray jsonArray = JSONArray.parseArray(paramInfo.getTableSql().trim());
                codeJavaInfo.setFieldList(processJsonObjectToFieldList(jsonArray.getJSONObject(0)));
            }
        }

        return codeJavaInfo;
    }

    /**
     * parse SQL by regex
     */
    public static ClassInfo processTableToClassInfoByRegex(ParamInfo paramInfo) {
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        ClassInfo codeJavaInfo = new ClassInfo();

        String DDL_PATTEN_STR = "\\s*create\\s+table\\s+(?<tableName>\\S+)[^\\(]*\\((?<columnsSQL>[\\s\\S]+)\\)[^\\)]+?(comment\\s*(=|on\\s+table)\\s*'(?<tableComment>.*?)'\\s*;?)?$";
        Pattern DDL_PATTERN = Pattern.compile(DDL_PATTEN_STR, Pattern.CASE_INSENSITIVE);
        String COL_PATTERN_STR = "\\s*(?<fieldName>\\S+)\\s+(?<fieldType>\\w+)\\s*(?:\\([\\s\\d,]+\\))?((?!comment).)*(comment\\s*'(?<fieldComment>.*?)')?\\s*(,|$)";
        Pattern COL_PATTERN = Pattern.compile(COL_PATTERN_STR, Pattern.CASE_INSENSITIVE);

        Matcher matcher = DDL_PATTERN.matcher(paramInfo.getTableSql().trim());
        if (matcher.find()) {
            String tableName2 = matcher.group("tableName");
            String tableComment = matcher.group("tableComment");
            codeJavaInfo.setTableName(tableName2.replaceAll("'", ""));
            codeJavaInfo.setClassName(tableName2.replaceAll("'", ""));
            codeJavaInfo.setClassComment(tableComment != null ? tableComment.replaceAll("'", "") : "");
            String columnsSQL = matcher.group("columnsSQL");
            if (columnsSQL != null && columnsSQL.length() > 0) {
                Matcher colMatcher = COL_PATTERN.matcher(columnsSQL);
                while (colMatcher.find()) {
                    String fieldName = colMatcher.group("fieldName");
                    String fieldType = colMatcher.group("fieldType");
                    String fieldComment = colMatcher.group("fieldComment");
                    if (!"key".equalsIgnoreCase(fieldType)) {
                        FieldInfo fieldInfo = new FieldInfo();
                        fieldInfo.setFieldName(fieldName.replaceAll("'", ""));
                        fieldInfo.setColumnName(fieldName.replaceAll("'", ""));
                        fieldInfo.setFieldClass(fieldType.replaceAll("'", ""));
                        fieldInfo.setFieldComment(fieldComment != null ? fieldComment.replaceAll("'", "") : "");
                        fieldList.add(fieldInfo);
                    }
                }
            }
            codeJavaInfo.setFieldList(fieldList);
        }
        return codeJavaInfo;
    }

    public static List<FieldInfo> processJsonObjectToFieldList(JSONObject jsonObject) {
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        jsonObject.keySet().stream().forEach(jsonField -> {
            FieldInfo fieldInfo = new FieldInfo();
            fieldInfo.setFieldName(jsonField);
            fieldInfo.setColumnName(jsonField);
            fieldInfo.setFieldClass(String.class.getSimpleName());
            fieldInfo.setFieldComment("father:" + jsonField);
            fieldList.add(fieldInfo);
            if (jsonObject.get(jsonField) instanceof JSONArray) {
                jsonObject.getJSONArray(jsonField).stream().forEach(arrayObject -> {
                    FieldInfo fieldInfo2 = new FieldInfo();
                    fieldInfo2.setFieldName(arrayObject.toString());
                    fieldInfo2.setColumnName(arrayObject.toString());
                    fieldInfo2.setFieldClass(String.class.getSimpleName());
                    fieldInfo2.setFieldComment("children:" + arrayObject.toString());
                    fieldList.add(fieldInfo2);
                });
            } else if (jsonObject.get(jsonField) instanceof JSONObject) {
                jsonObject.getJSONObject(jsonField).keySet().stream().forEach(arrayObject -> {
                    FieldInfo fieldInfo2 = new FieldInfo();
                    fieldInfo2.setFieldName(arrayObject.toString());
                    fieldInfo2.setColumnName(arrayObject.toString());
                    fieldInfo2.setFieldClass(String.class.getSimpleName());
                    fieldInfo2.setFieldComment("children:" + arrayObject.toString());
                    fieldList.add(fieldInfo2);
                });
            }
        });
        if (fieldList.size() < 1) {
            throw new CodeGenerateException("JSON解析失败");
        }
        return fieldList;
    }

    public static ClassInfo processInsertSqlToClassInfo(ParamInfo paramInfo) {
        List<FieldInfo> fieldList = new ArrayList<FieldInfo>();
        ClassInfo codeJavaInfo = new ClassInfo();

        String fieldSqlStr = paramInfo.getTableSql().toLowerCase().trim();
        fieldSqlStr = fieldSqlStr.replaceAll("  ", " ").replaceAll("\\\\n`", "")
                .replaceAll("\\+", "").replaceAll("``", "`").replaceAll("\\\\", "");
        String valueStr = fieldSqlStr.substring(fieldSqlStr.lastIndexOf("values") + 6).replaceAll(" ", "")
                .replaceAll("\\(", "").replaceAll("\\)", "");
        fieldSqlStr = fieldSqlStr.substring(0, fieldSqlStr.lastIndexOf("values"));

        String insertSqlPattenStr = "insert into (?<tableName>.*) \\((?<columnsSQL>.*)\\)";
        Matcher matcher1 = Pattern.compile(insertSqlPattenStr).matcher(fieldSqlStr);
        while (matcher1.find()) {
            String tableName = matcher1.group("tableName");
            codeJavaInfo.setClassName(tableName);
            codeJavaInfo.setTableName(tableName);

            String columnsSQL = matcher1.group("columnsSQL");
            List<String> valueList = new ArrayList<>();
            Arrays.stream(valueStr.split(",")).forEach(column -> valueList.add(column));
            AtomicInteger n = new AtomicInteger(0);
            Arrays.stream(columnsSQL.replaceAll(" ", "").split(",")).forEach(column -> {
                FieldInfo fieldInfo2 = new FieldInfo();
                fieldInfo2.setFieldName(column);
                fieldInfo2.setColumnName(column);
                fieldInfo2.setFieldClass(String.class.getSimpleName());
                if (n.get() < valueList.size()) {
                    fieldInfo2.setFieldComment(column + " , eg." + valueList.get(n.get()));
                }
                fieldList.add(fieldInfo2);
                n.getAndIncrement();
            });
        }
        if (fieldList.size() < 1) {
            throw new CodeGenerateException("INSERT SQL解析失败");
        }
        codeJavaInfo.setFieldList(fieldList);
        return codeJavaInfo;
    }

}