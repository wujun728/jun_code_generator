package cn.afterturn.gen.core.db.convert.sql.impl;

import cn.afterturn.gen.core.model.GenBeanEntity;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlConvertOfOracleTest {

    private String sql = "CREATE TABLE \"HR\".\"test\" (\n" +
            "\"id\" NUMBER(10) NOT NULL ,\n" +
            "\"name\" VARCHAR2(15) NULL ,\n" +
            "\"sex\" NUMBER(2) NULL \n" +
            ");\n" +
            "COMMENT ON TABLE \"HR\".\"test\" IS '测试';\n" +
            "COMMENT ON COLUMN \"HR\".\"test\".\"name\" IS '名字';\n" +
            "COMMENT ON COLUMN \"HR\".\"test\".\"sex\" IS '性别';";

    @Test
    public void parseSql() throws Exception {
        GenBeanEntity beanEntity = new SqlConvertOfOracle().parseSql(sql);
    }

}