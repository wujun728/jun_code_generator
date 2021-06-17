package com.jun.plugin.code_generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.jun.plugin.code_generator.util.CommonUtils;

import org.junit.Test;

public class CodeGeneratorTests {

    @Test
    public void testMySQLCodeGenerator() {
        DbType dbType = DbType.MYSQL;
        String dbUrl = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        // 表前缀，生成的实体类，不含前缀
        String [] tablePrefixes = {"t_"};
        // 表名，为空，生成所有的表
        String [] tableNames = {};
        // 字段前缀
        String [] fieldPrefixes = {};
        // 基础包名
        String packageName = "com.jun.plugin.code_generator.db";
        CommonUtils.execute(dbType, dbUrl, username, password, driver, tablePrefixes, tableNames, packageName, fieldPrefixes);
    }

}
