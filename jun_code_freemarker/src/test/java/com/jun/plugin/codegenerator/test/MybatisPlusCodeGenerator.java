package com.jun.plugin.codegenerator.test;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// * select GROUP_CONCAT(table_name) from information_schema.tables where table_schema='vtis_ycxc' and TABLE_NAME like 'jc_%'
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class MybatisPlusCodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
    	mpgExecute();
    }
	
    public static void mpgExecute() {
        String packageCompany = "jun";
        String packageProject = "plugin";
        
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        //当前工程路径
        String projectPath = System.getProperty("user.dir");
//        System.out.println(projectPath);
        gc.setOutputDir(projectPath + "/src/main/java");//生成文件的输出目录【默认 D 盘根目录】
        gc.setAuthor("wujun");//开发人员
        gc.setOpen(false);//是否打开输出目录
        gc.setFileOverride(false);// 是否覆盖已有同名文件，默认是false
        gc.setActiveRecord(true);// 开启 ActiveRecord 模式，默认是false
        gc.setEnableCache(false);// 是否在xml中添加二级缓存配置，默认是false
        gc.setBaseResultMap(true);// 开启 BaseResultMap，默认是false
        gc.setBaseColumnList(true);// 开启 baseColumnList，默认是false
        gc.setSwagger2(true);//实体属性 Swagger2 注解
        /*
        * 各层文件名称方式，例如： %sAction 生成 UserAction
        * %s 为占位符，注意 %s 会自动填充表实体属性！
        */
         //gc.setMapperName("%sDao");//默认UserMapper.xml
         //gc.setXmlName("%sDao");//默认UserMapper.xml
         //gc.setServiceName("MP%sService");//默认IUserService.java
         //gc.setServiceImplName("%sServiceDiy");//默认UserServiceImpl.java
         //gc.setControllerName("%sAction");//默认UserController.java
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/test666?useUnicode=true&useSSL=false&characterEncoding=utf8");
        //dsc.setSchemaName("public");//PostgreSQL schemaName
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));//父包模块名,默认null
        //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent("com.jun.plugin.moduleName");//默认com.baomidou
        //pc.setController("wujunController");//默认controller
        //pc.setService("wujunService");//默认service
        //pc.setServiceImpl("wujunService.impl");//默认service.impl
        //pc.setEntity("wujunEntity");//默认entity
        //pc.setMapper("wujunMapper");//默认mapper
        //pc.setXml("wujunMapper.xml");//默认mapper.xml
//        pc.setParent("com." + packageCompany + "");
//        String packagefunction = scanner("功能目录:");
//        pc.setController("controller." + packagefunction);
//        pc.setEntity("entity." + packagefunction);
//        pc.setService("service." + packagefunction);
//        pc.setServiceImpl("service." + packagefunction + ".impl");
//        pc.setMapper("dao." + packagefunction);
//        pc.setXml("mapper");
        mpg.setPackageInfo(pc);
        

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        //  String templatePath = "/templates/mapper.xml.vm";
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
         templateConfig.setEntity("/templates/entity.java");
         templateConfig.setService("/templates/service.java");
         templateConfig.setServiceImpl("/templates/serviceImpl.java");
         templateConfig.setController("/templates/controller.java");
//        String TEMPLATE_ENTITY_JAVA = "/templates/entity.java";
//        String TEMPLATE_ENTITY_KT = "/templates/entity.kt";
//        String TEMPLATE_MAPPER = "/templates/mapper.java";
//        String TEMPLATE_XML = "/templates/mapper.xml";
//        String TEMPLATE_SERVICE = "/templates/service.java";
//        String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
//        String TEMPLATE_CONTROLLER = "/templates/controller.java";
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        //数据库表映射到实体的命名策略，默认：不做任何改变，原样输出
        strategy.setNaming(NamingStrategy.underline_to_camel);
        //数据库表字段映射到实体的命名策略，未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //【实体】是否为lombok模型（默认 false）
        //strategy.setEntityLombokModel(true);
        //生成@RestController 控制器
        strategy.setRestControllerStyle(true);
        //自定义继承的Controller类全称，带包名
        //strategy.setSuperControllerClass("com.mybatisplus.demo.common.BaseController");
        //strategy.setSuperServiceClass();
        //strategy.setSuperServiceImplClass();
        //strategy.setSuperMapperClass("com.mybatisplus.demo.common.BaseMapper");
        //strategy.setSuperEntityClass("com.mybatisplus.demo.common.BaseEntity");//自定义继承的Entity类全称，带包名

        strategy.setInclude(scanner("表名"));//表明String数组
        strategy.setSuperEntityColumns("id");
        // 驼峰转连字符:
        // @RequestMapping("/managerUserActionHistory")-->@RequestMapping("/manager-user-action-history")
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");//表前缀

        mpg.setStrategy(strategy);//数据库表配置
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());// 选择 freemarker 引擎，默认 Veloctiy

        //生成代码
        mpg.execute();
    }
    
    

/**
 *
 java.version          Java 运行时环境版本
 java.vendor         Java 运行时环境供应商
 java.vendor.url         Java 供应商的 URL
 java.vm.specification.version         Java 虚拟机规范版本
 java.vm.specification.vendor         Java 虚拟机规范供应商
 java.vm.specification.name         Java 虚拟机规范名称
 java.vm.version         Java 虚拟机实现版本
 java.vm.vendor         Java 虚拟机实现供应商
 java.vm.name         Java 虚拟机实现名称
 java.specification.version         Java 运行时环境规范版本
 java.specification.vendor         Java 运行时环境规范供应商
 java.specification.name         Java 运行时环境规范名称
 os.name         操作系统的名称
 os.arch         操作系统的架构
 os.version         操作系统的版本
 file.separator         文件分隔符（在 UNIX 系统中是“ / ”）
 path.separator         路径分隔符（在 UNIX 系统中是“ : ”）
 line.separator         行分隔符（在 UNIX 系统中是“ /n ”）

 java.home         Java 安装目录
 java.class.version         Java 类格式版本号
 java.class.path         Java 类路径
 java.library.path          加载库时搜索的路径列表
 java.io.tmpdir         默认的临时文件路径
 java.compiler         要使用的 JIT 编译器的名称
 java.ext.dirs         一个或多个扩展目录的路径
 user.name         用户的账户名称
 user.home         用户的主目录
 user.dir
 */


    public static void MybatisPlusCodeGenerator() throws MalformedURLException, URISyntaxException {

        System.out.println("java.home : "+System.getProperty("java.home"));

        System.out.println("java.class.version : "+System.getProperty("java.class.version"));

        System.out.println("java.class.path : "+System.getProperty("java.class.path"));

        System.out.println("java.library.path : "+System.getProperty("java.library.path"));

        System.out.println("java.io.tmpdir : "+System.getProperty("java.io.tmpdir"));

        System.out.println("java.compiler : "+System.getProperty("java.compiler"));

        System.out.println("java.ext.dirs : "+System.getProperty("java.ext.dirs"));

        System.out.println("user.name : "+System.getProperty("user.name"));

        System.out.println("user.home : "+System.getProperty("user.home"));

        System.out.println("user.dir : "+System.getProperty("user.dir"));

        System.out.println("===================");

        System.out.println("package: "+MybatisPlusCodeGenerator.class.getPackage().getName());

        System.out.println("package: "+MybatisPlusCodeGenerator.class.getPackage().toString());

        System.out.println("=========================");

        String packName = MybatisPlusCodeGenerator.class.getPackage().getName();

                /*URL packurl = new URL(packName);
                System.out.println(packurl.getPath());*/

        URI packuri = new URI(packName);

        System.out.println(packuri.getPath());

        //System.out.println(packuri.toURL().getPath());

        System.out.println(packName.replaceAll("//.", "/"));

        System.out.println(System.getProperty("user.dir")+"/"+(MybatisPlusCodeGenerator.class.getPackage().getName()).replaceAll("//.", "/")+"/");

    }


}
