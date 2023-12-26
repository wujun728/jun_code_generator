package com.jun.plugin.codegenerator;



import com.jun.plugin.codegenerator.core.CodeGeneratorUtils;
import org.junit.Test;

import static com.jun.plugin.codegenerator.core.CodeGeneratorUtils.genTables;

public class CodeGeneratorTest {

    @Test
    public void codeGenerator() throws Exception {
        String tables = "t_admin";
//		String tables = "git_user";
//		String tables = "app_infoenvt,app_member,app_datasource,app_git_config,git_user,app_deploy_config";
        String  jarLocal = new String(CodeGeneratorUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath().getBytes(), "UTF-8");
        String url8 = CodeGeneratorUtils.class.getResource("/").getPath();
        System.out.println( url8);
        System.out.println( jarLocal);
        System.out.println( System.getProperty("user.dir"));

        String s = CodeGeneratorUtils.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "templates";
        System.out.println("---- "+ s);

        genTables(tables.split(","));

    }

}
