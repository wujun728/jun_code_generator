package io.github.wujun728.mybatisplus.generator;

import io.github.wujun728.mybatisplus.model.GenerModel;
import io.github.wujun728.mybatisplus.utils.GenUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Comparator;

/**
 * CrudGenerator 单元测试
 *
 * 测试数据源: localhost:3307/db_test (root/mysqladmin)
 * 测试表: biz_test (自动创建，覆盖多种字段类型)
 */
class CrudGeneratorTest {

    private static final String OUTPUT_DIR = "target/generator-test-output/";

    private static final String DB_URL = "jdbc:mysql://localhost:3307/db_test?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "mysqladmin";

    @BeforeAll
    static void setup() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             Statement stmt = conn.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS biz_test");

            stmt.execute(
                "CREATE TABLE biz_test (" +
                "  id bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID'," +
                "  name varchar(100) DEFAULT NULL COMMENT '名称'," +
                "  code varchar(50) DEFAULT NULL COMMENT '编码'," +
                "  age int(11) DEFAULT NULL COMMENT '年龄'," +
                "  email varchar(200) DEFAULT NULL COMMENT '邮箱'," +
                "  phone varchar(20) DEFAULT NULL COMMENT '电话'," +
                "  status tinyint(1) DEFAULT 1 COMMENT '状态:0禁用,1启用'," +
                "  score decimal(10,2) DEFAULT NULL COMMENT '分数'," +
                "  content text COMMENT '内容'," +
                "  birthday date DEFAULT NULL COMMENT '生日'," +
                "  work_time datetime DEFAULT NULL COMMENT '工作时间'," +
                "  create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'," +
                "  update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'," +
                "  is_deleted tinyint(1) DEFAULT 0 COMMENT '逻辑删除:0未删,1已删'," +
                "  PRIMARY KEY (id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务测试表'");

            System.out.println("✓ 测试表 biz_test 创建成功");
        }
    }

    private GenerModel buildTestModel() {
        GenerModel model = new GenerModel();
        model.setDbUrl(DB_URL);
        model.setDriverName("com.mysql.cj.jdbc.Driver");
        model.setUsername(DB_USER);
        model.setPassword(DB_PASS);
        model.setPack(OUTPUT_DIR + "java");
        model.setOutputDirXml(OUTPUT_DIR + "resources/mapper");
        model.setParentPackage("com.test.modules");
        model.setModules("test");
        model.setAuthor("test");
        model.setEnableSwaggerV3(true);
        model.setTemplateGroup("curd");
        model.setTableName("biz_test");
        model.setPrefix("");
        return model;
    }

    private void cleanOutputDir() throws Exception {
        Path outputPath = Paths.get(OUTPUT_DIR);
        if (Files.exists(outputPath)) {
            Files.walk(outputPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    private void assertFileExists(String relativePath) {
        File file = new File(OUTPUT_DIR + "java/" + relativePath);
        assert file.exists() : "文件不存在: " + file.getAbsolutePath();
        assert file.length() > 0 : "文件为空: " + file.getAbsolutePath();
        System.out.println("✓ 生成文件: " + file.getAbsolutePath());
    }

    @Test
    void testCurdTemplateGroup() throws Exception {
        System.out.println("\n========== 测试: curd 模板组 ==========");
        cleanOutputDir();

        GenerModel model = buildTestModel();
        model.setTemplateGroup("curd");
        model.setEnableSuperClass(false);

        GenUtils.buildAndExecute(model);

        assertFileExists("com/test/modules/test/controller/BizTestController.java");
        assertFileExists("com/test/modules/test/entity/BizTest.java");
        assertFileExists("com/test/modules/test/service/BizTestService.java");
        assertFileExists("com/test/modules/test/service/impl/BizTestServiceImpl.java");
        assertFileExists("com/test/modules/test/mapper/BizTestMapper.java");

        File xmlFile = new File(OUTPUT_DIR + "resources/mapper/BizTestMapper.xml");
        assert xmlFile.exists() : "XML文件不存在: " + xmlFile.getAbsolutePath();
        System.out.println("✓ 生成XML文件: " + xmlFile.getAbsolutePath());

        System.out.println("========== curd 模板组测试通过 ==========\n");
    }

    @Test
    void testSimpleTemplateGroupWithoutSuper() throws Exception {
        System.out.println("\n========== 测试: simple 模板组(无父类) ==========");
        cleanOutputDir();

        GenerModel model = buildTestModel();
        model.setTemplateGroup("simple");
        model.setEnableSuperClass(false);

        GenUtils.buildAndExecute(model);

        assertFileExists("com/test/modules/test/controller/BizTestController.java");
        assertFileExists("com/test/modules/test/entity/BizTest.java");
        assertFileExists("com/test/modules/test/service/BizTestService.java");
        assertFileExists("com/test/modules/test/service/impl/BizTestServiceImpl.java");
        assertFileExists("com/test/modules/test/mapper/BizTestMapper.java");

        System.out.println("========== simple 模板组(无父类)测试通过 ==========\n");
    }

    @Test
    void testSimpleTemplateGroupWithSuper() throws Exception {
        System.out.println("\n========== 测试: simple 模板组(启用父类) ==========");
        cleanOutputDir();

        GenerModel model = buildTestModel();
        model.setTemplateGroup("simple");
        model.setEnableSuperClass(true);
        model.setSuperControllerClass("com.dcy.db.base.controller.BaseController");
        model.setSuperEntityClass("com.dcy.db.base.model.BaseModel");
        model.setSuperServiceClass("com.dcy.db.base.service.BaseService");
        model.setSuperServiceImplClass("com.dcy.db.base.service.impl.BaseServiceImpl");
        model.setSuperEntityColumns("createBy,createDate,updateBy,updateDate,delFlag,remark");

        GenUtils.buildAndExecute(model);

        assertFileExists("com/test/modules/test/controller/BizTestController.java");
        assertFileExists("com/test/modules/test/entity/BizTest.java");
        assertFileExists("com/test/modules/test/service/BizTestService.java");
        assertFileExists("com/test/modules/test/service/impl/BizTestServiceImpl.java");
        assertFileExists("com/test/modules/test/mapper/BizTestMapper.java");

        System.out.println("========== simple 模板组(启用父类)测试通过 ==========\n");
    }

    @Test
    void testCurdWithSwaggerV2() throws Exception {
        System.out.println("\n========== 测试: curd 模板组 + Swagger V2 ==========");
        cleanOutputDir();

        GenerModel model = buildTestModel();
        model.setTemplateGroup("curd");
        model.setEnableSwaggerV3(false);

        GenUtils.buildAndExecute(model);

        assertFileExists("com/test/modules/test/controller/BizTestController.java");
        assertFileExists("com/test/modules/test/entity/BizTest.java");

        System.out.println("========== curd + Swagger V2 测试通过 ==========\n");
    }

    @Test
    void testCurdWithDto() throws Exception {
        System.out.println("\n========== 测试: curd 模板组 + DTO ==========");
        cleanOutputDir();

        GenerModel model = buildTestModel();
        model.setTemplateGroup("curd");
        model.setDtoEnabled(true);

        GenUtils.buildAndExecute(model);

        assertFileExists("com/test/modules/test/controller/BizTestController.java");

        File inputDto = new File(OUTPUT_DIR + "java/com/test/modules/test/dto/input/BizTestInputDTO.java");
        assert inputDto.exists() : "InputDTO文件不存在: " + inputDto.getAbsolutePath();
        System.out.println("✓ 生成InputDTO文件: " + inputDto.getAbsolutePath());

        File outputDto = new File(OUTPUT_DIR + "java/com/test/modules/test/dto/output/BizTestOutputDTO.java");
        assert outputDto.exists() : "OutputDTO文件不存在: " + outputDto.getAbsolutePath();
        System.out.println("✓ 生成OutputDTO文件: " + outputDto.getAbsolutePath());

        System.out.println("========== curd + DTO 测试通过 ==========\n");
    }

    @Test
    void testLoadProperties() {
        System.out.println("\n========== 测试: 加载 generator.properties ==========");

        GenerModel model = GenUtils.getGenerByProps();

        assert model.getDbUrl() != null : "数据库URL不应为空";
        assert model.getUsername() != null : "用户名不应为空";
        assert model.getTableName() != null : "表名不应为空";
        assert model.getTemplateGroup() != null : "模板组不应为空";

        System.out.println("DB URL: " + model.getDbUrl());
        System.out.println("Tables: " + model.getTableName());
        System.out.println("Template Group: " + model.getTemplateGroup());

        System.out.println("========== 配置加载测试通过 ==========\n");
    }

    @Test
    void testCurdWithVue() throws Exception {
        System.out.println("\n========== 测试: curd 模板组 + Vue ==========");
        cleanOutputDir();

        GenerModel model = buildTestModel();
        model.setTemplateGroup("curd");
        model.setVueEnabled(true);

        GenUtils.buildAndExecute(model);

        assertFileExists("com/test/modules/test/controller/BizTestController.java");

        File vueFile = new File(OUTPUT_DIR + "java/com/test/modules/test/BizTest-manage.vue");
        assert vueFile.exists() : "Vue文件不存在: " + vueFile.getAbsolutePath();
        System.out.println("✓ 生成Vue文件: " + vueFile.getAbsolutePath());

        File jsFile = new File(OUTPUT_DIR + "java/com/test/modules/test/BizTest.js");
        assert jsFile.exists() : "JS API文件不存在: " + jsFile.getAbsolutePath();
        System.out.println("✓ 生成JS API文件: " + jsFile.getAbsolutePath());

        System.out.println("========== curd + Vue 测试通过 ==========\n");
    }
}