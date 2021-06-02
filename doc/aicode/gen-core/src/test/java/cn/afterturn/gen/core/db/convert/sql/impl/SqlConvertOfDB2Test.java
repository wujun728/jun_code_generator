package cn.afterturn.gen.core.db.convert.sql.impl;

import cn.afterturn.gen.core.model.GenBeanEntity;
import org.junit.Test;

import static org.junit.Assert.*;

public class SqlConvertOfDB2Test {
    private  String sql = "Create table ods.XD_BAIDUXD_LOAN_RATE (  \n" +
            "id int NOT NULL,\n" +
            "CUR_DATE VARCHAR(8) NOT NULL,\n" +
            "LOAN_ID VARCHAR(30) NOT NULL,\n" +
            "START_TERM DECIMAL(14,4) NOT NULL,\n" +
            "END_TERM DECIMAL(14,4) NOT NULL,\n" +
            "INT_RATE DECIMAL(14,4) NOT NULL,\n" +
            "  PRIMARY KEY  (id)\n" +
            ");   \n" +
            "Comment on Table ods.XD_BAIDUXD_LOAN_RATE is '贷款利息费率';    \n" +
            "Comment on Column ods.XD_BAIDUXD_LOAN_RATE.CUR_DATE is '账务日期 :对账日期';\n" +
            "Comment on Column ods.XD_BAIDUXD_LOAN_RATE.LOAN_ID is '借据号 :贷款借据号';\n" +
            "Comment on Column ods.XD_BAIDUXD_LOAN_RATE.START_TERM is '开始期序 :开始期序';\n" +
            "Comment on Column ods.XD_BAIDUXD_LOAN_RATE.END_TERM is '结束期序 :结束期序';\n" +
            "Comment on Column ods.XD_BAIDUXD_LOAN_RATE.INT_RATE is '正常利率 :正常利率（万分之）';\n";

    @Test
    public void parseSql() throws Exception {
        GenBeanEntity beanEntity =  new SqlConvertOfDB2().parseSql(sql);
    }

}