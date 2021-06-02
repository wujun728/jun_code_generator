package cn.afterturn.gen.core.db.convert.sql.impl;

import cn.afterturn.gen.core.db.convert.sql.ISqlConvert;
import cn.afterturn.gen.core.model.GenBeanEntity;
import cn.afterturn.gen.core.model.GenFieldEntity;
import cn.afterturn.gen.core.model.enmus.BooleanType;
import cn.afterturn.gen.core.model.enmus.DBType;
import cn.afterturn.gen.core.util.NameUtil;
import cn.afterturn.gen.core.util.TableHandlerUtil;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Oracle 语句解析
 *
 * @author JueYue on 2017/10/25.
 */
public class SqlConvertOfOracle implements ISqlConvert {

    @Override
    public GenBeanEntity parseSql(String sql) {
        sql = sql.replace("`", "");
        sql = sql.replace("\"", "");
        sql = SQLUtils.format(sql, JdbcConstants.ORACLE, new SQLUtils.FormatOption());
        GenBeanEntity bean = new GenBeanEntity();
        String tableName = sql.substring(sql.indexOf("TABLE") + 5, sql.indexOf("(")).trim();
        String newTableName = NameUtil.handlerDBName(tableName);
        if (!newTableName.equals(tableName)) {
            sql = sql.replace(tableName, newTableName);
            tableName = newTableName;
        }
        bean.setTableName(tableName);
        bean.setName(NameUtil.getEntityHumpName(bean.getTableName()));
        String[] columns = sql.substring(sql.indexOf("(") + 1).split("\n");
        List<GenFieldEntity> fields = new ArrayList<GenFieldEntity>(columns.length);
        GenFieldEntity field;
        for (int i = 0; i < columns.length; i++) {
            if (StringUtils.isBlank(columns[i])) {
                continue;
            }
            if (columns[i].contains("PRIMARY")) {
                //设置主键
                String[] keys = columns[i].substring(columns[i].indexOf("(") + 1, columns[i].indexOf(")")).split(",");
                for (int j = 0; j < keys.length; j++) {
                    for (int k = 0; k < fields.size(); k++) {
                        if (fields.get(k).getFieldName().equalsIgnoreCase(keys[j])) {
                            fields.get(k).setKey(BooleanType.YES.getIntD());
                            break;
                        }
                    }
                }
            } else if (columns[i].contains("COMMENT")) {
                String[] keys = columns[i].trim().split(" ");
                if (columns[i].contains("TABLE")) {
                    String comment = getFieldComment(keys);
                    bean.setComment(comment);
                    bean.setChinaName(bean.getComment());
                } else if (columns[i].contains("COLUMN")) {
                    String column = keys[3].replace(bean.getTableName() + ".", "");
                    String comment = getFieldComment(keys);
                    for (int k = 0; k < fields.size(); k++) {
                        if (fields.get(k).getFieldName().equalsIgnoreCase(column)) {
                            fields.get(k).setChinaName(comment);
                            fields.get(k).setComment(comment);
                            break;
                        }
                    }
                }
            } else {
                field = new GenFieldEntity();
                String[] keys = columns[i].trim().split(" ");
                if (keys.length < 2) {
                    continue;
                }
                field.setFieldName(NameUtil.handlerDBName(keys[0]));
                if (keys[1].indexOf("(") > 0) {
                    String type = keys[1];
                    field.setFieldType(type.substring(0, type.indexOf("(")));
                    if (type.indexOf(")") < 0) {
                        for (int j = 2; j < keys.length; j++) {
                            if (keys[j].indexOf(")") > -1) {
                                type += keys[j];
                                break;
                            }
                            type += keys[j];
                        }
                    }
                    String[] points = type.substring(type.indexOf("(") + 1, type.indexOf(")")).split(",");
                    field.setFieldLength(Integer.parseInt(points[0]));
                    if (points.length == 2) {
                        field.setFieldPointLength(Integer.parseInt(points[1]));
                    }
                } else {
                    field.setFieldType(keys[1]);
                }
                field.setNotNull(columns[i].contains("NOT NULL") ? 2 : 1);
                fields.add(field);
            }
        }
        TableHandlerUtil.handlerFields(fields, getDbType());
        bean.setFields(fields);
        return bean;
    }

    private String getFieldComment(String[] keys) {
        StringBuilder sb = new StringBuilder();
        for (int i = 5; i < keys.length; i++) {
            sb.append(keys[i]);
        }
        return sb.toString().replace("'", "").replace(";", "");
    }

    @Override
    public DBType getDbType() {
        return DBType.ORACLE;
    }

}
