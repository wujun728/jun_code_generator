package cn.afterturn.gen.core.db.convert.sql.impl;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.util.JdbcConstants;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlConvertOfMysqlTest {

    private  String sql = "create table \"spider_batch_history\" (\n" +
            "  \"id\" int(10) NOT NULL AUTO_INCREMENT,\n" +
            "  \"site_id\" int(10) NOT NULL,\n" +
            "  \"type\" int(1) NOT NULL COMMENT '类型',\n" +
            "  \"nums\" int(10) DEFAULT NULL COMMENT '数据数量',\n" +
            "  \"status\" int(1) DEFAULT NULL COMMENT '状态 1 初始化 2执行中 3获取完成 4获取失败 5推送中 6推送完成 7推送失败',\n" +
            "  \"date\" varchar(20) DEFAULT NULL COMMENT '执行日期',\n" +
            "  \"create_time\" datetime NOT NULL,\n" +
            "  \"complate_time\" datetime DEFAULT NULL,\n" +
            "  PRIMARY KEY (\"id\") USING BTREE\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='跑批记录表';";

    @Test
    public void parseSql() {
        //sql = sql.replaceAll("\"","`");
        //sql = sql.replace("`", " ");
        sql = sql.replace("USING BTREE", " ");
        sql = SQLUtils.format(sql, JdbcConstants.MYSQL, SQLUtils.DEFAULT_FORMAT_OPTION);
        System.out.printf(sql);
    }
}