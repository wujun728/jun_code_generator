package net.trueland.scrm.paas.runtime.extender.standard.crm.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import net.trueland.scrm.common.model.exception.BuException;
import net.trueland.scrm.paas.runtime.extender.standard.crm.generator.conf.GeneratorProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static net.trueland.scrm.paas.runtime.extender.standard.crm.generator.conf.GeneratorProperties.*;


/**
 * 基础配置类
 *
 * @author bright
 * @date 2024/1/17 14:49
 */
@Slf4j
public class CodeGenerator {
    // 初始化properties属性
    static GeneratorProperties generatorProperties = new GeneratorProperties();
    /**
     * 全局配置
     */
    protected static GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder();
    }

    private final DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig
            .Builder(URL, USERNAME, PASSWORD)
            .schema(DBNAME);


    protected FastAutoGenerator autoGenerator() {
        return FastAutoGenerator.create(dataSourceConfig);
    }


    /**
     * 获取路径信息map
     *
     * @return {@link Map < OutputFile, String> }
     */
    private static Map<OutputFile, String> getPathInfo() {
        EnumMap<OutputFile, String> pathInfo = new EnumMap<>(OutputFile.class);
        pathInfo.put(OutputFile.entity, getPath(PATH_ENTITY));
        pathInfo.put(OutputFile.mapper, getPath(PATH_MAPPER));
        pathInfo.put(OutputFile.service, getPath(PATH_SERVICE));
        pathInfo.put(OutputFile.serviceImpl, getPath(PATH_SERVICE_IMPL));
        pathInfo.put(OutputFile.controller, getPath(PATH_CONTROLLER));
        pathInfo.put(OutputFile.xml, getPath(PATH_XML));
        return pathInfo;
    }

    private static String getPath(String path) {
        //String parentStr = PARENT_DIR.substring(0, PARENT_DIR.lastIndexOf("\\"));
        String parentStr = PARENT_DIR ;
        return parentStr + path;
    }

    /**
     * 表配置三种场景:
     * (1) mysql.tables = 表名 生成指定表
     * (2) mysql.tables = all 生成指定库所有表
     * (3) mysql.tables 没有配置  默认生成当前租户的下的业务表  默认
     *
     * @return 返回需要生成的表
     */
    private List<String> getTables() {
        return Arrays.asList(TABLES.split(","));
    }

    private String convertEntityName(String entityName) {
        if (entityName.length() <= 14) {
            return entityName;
        }
        return entityName.substring(7, entityName.length() - 7);
    }


    protected void execute() {
        FastAutoGenerator generator = autoGenerator();
        // 策略配置
        generator.strategyConfig(builder ->
                        // 配置表
                        builder.addInclude(getTables())

                                // controller 层配置
                                .controllerBuilder()
                                .enableRestStyle()

                                .convertFileName(entityName -> convertEntityName(entityName) + "Controller")
                                .build()

                                // mapper 层配置
                                .mapperBuilder().enableMapperAnnotation()
                                .enableBaseColumnList()
                                .enableBaseResultMap()
                                .convertMapperFileName(entityName -> convertEntityName(entityName) + "Mapper")
                                .convertXmlFileName(entityName -> convertEntityName(entityName) + "Mapper")

                                // entity 配置
                                .entityBuilder()
                                .enableLombok()
                                .enableTableFieldAnnotation()
                                .enableChainModel()
                                .convertFileName(entityName -> convertEntityName(entityName) + "Entity")

                                // service 配置
                                .serviceBuilder()
                                .convertServiceFileName(entityName -> "I" + convertEntityName(entityName) + "Service")
                                .convertServiceImplFileName(entityName -> convertEntityName(entityName) + "ServiceImpl")

                )

                // 全局配置
                .globalConfig(builder -> globalConfig().build())

                // 包路径配置
                .packageConfig(builder -> builder.parent(PACKAGE_PATH)
                        .entity("model.entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller")
                        .pathInfo(getPathInfo()))

                // 模板路径配置
                .templateConfig(builder -> builder.disable(TemplateType.ENTITY)
                        .entity("/templates/entity.java")
                        .service("/templates/service.java")
                        .serviceImpl("/templates/serviceImpl.java")
                        .mapper("/templates/mapper.java")
                        .xml("/templates/mapper.xml")
                        .controller("/templates/controller.java"))

                // 设置模板引擎
                .templateEngine(new FreemarkerTemplateEngine())

                // 执行
                .execute();
    }


    /**
     * 主方法：启动 MyBatis-Plus 代码生成器
     * 作用：实例化 BaseGenerator 并调用 execute() 方法执行代码生成逻辑
     * @param args 命令行参数（当前无需传入，可留空）
     */
    public static void main(String[] args) {
        try {
            log.info("=============== MyBatis-Plus 代码生成器开始执行 ===============");
            // 实例化当前类，调用 execute() 方法启动生成
            CodeGenerator baseGenerator = new CodeGenerator();
            baseGenerator.execute();
            log.info("=============== MyBatis-Plus 代码生成器执行成功 ===============");
        } catch (BuException e) {
            log.error("=============== 代码生成器执行失败：业务异常 ===============", e);
            // 抛出运行时异常，终止程序（方便感知执行失败）
            throw new RuntimeException(e);
        } catch (Exception e) {
            log.error("=============== 代码生成器执行失败：未知异常 ===============", e);
            throw new RuntimeException(e);
        }
    }
}