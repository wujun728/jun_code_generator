package io.github.wujun728.mybatisplus.model;

import lombok.Data;

/**
 * 代码生成器参数模型
 *
 * @author wujun
 */
@Data
public class GenerModel {

    /** 输出路径 */
    private String pack;
    /** 数据库URL */
    private String dbUrl;
    /** 驱动类名 */
    private String driverName;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;
    /** 表名(逗号分隔) */
    private String tableName;
    /** 模块名(英文) */
    private String modules;
    /** 表前缀 */
    private String prefix;

    // ===== 模板与生成配置 =====

    /** 模板组: curd(完整CRUD方法体) / simple(父类继承) */
    private String templateGroup = "curd";
    /** 包路径 */
    private String parentPackage = "com.example.modules";
    /** XML输出目录 */
    private String outputDirXml;
    /** 是否启用Swagger V3 (true=v3/springdoc, false=v2/swagger2) */
    private boolean enableSwaggerV3 = true;
    /** 是否启用父类继承(simple模板组有效) */
    private boolean enableSuperClass = false;
    /** 作者名 */
    private String author = "admin";
    /** 响应类全限定名(curd模板Controller使用) */
    private String responseClass;
    /** 是否生成DTO */
    private boolean dtoEnabled = false;
    /** 是否生成Vue页面 */
    private boolean vueEnabled = false;
    /** 是否生成微服务Feign接口 */
    private boolean microserviceEnabled = false;

    // ===== 父类配置(simple模板组enableSuperClass=true时有效) =====

    /** 父类Controller全限定名 */
    private String superControllerClass;
    /** 父类Entity全限定名 */
    private String superEntityClass;
    /** 父类Service全限定名 */
    private String superServiceClass;
    /** 父类ServiceImpl全限定名 */
    private String superServiceImplClass;
    /** 父类排除字段 */
    private String superEntityColumns;
}