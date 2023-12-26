package com.jun.plugin.codegenerator.test;



import com.jun.plugin.codegenerator.admin.core.CodeGeneratorTool;
import com.jun.plugin.codegenerator.core.model.ClassInfo;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class CodeGeneratorToolTest {

    @Test
    public void codeGeneratorToolTest() throws IOException  {

        // table sql
        FileInputStream fileInputStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("table.sql").getPath());
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

        StringBuffer stringBuffer = new StringBuffer();
        String lineTmp = null;
        while ((lineTmp = bufferedReader.readLine()) != null) {
            stringBuffer.append(lineTmp);
        }
        bufferedReader.close();

        String tableSql = stringBuffer.toString();

        // code generate
        ClassInfo classInfo = CodeGeneratorTool.processTableIntoClassInfo(tableSql);

    }

}
