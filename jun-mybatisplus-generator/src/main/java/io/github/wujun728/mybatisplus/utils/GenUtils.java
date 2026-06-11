package io.github.wujun728.mybatisplus.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.setting.dialect.Props;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.builder.CustomFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import io.github.wujun728.mybatisplus.model.GenerModel;

import java.util.*;

/**
 * MyBatis-Plus 3.5.3.1 FastAutoGenerator 工具类
 */
public class GenUtils {

    private static final Props props;

    static {
        props = new Props("generator.properties", CharsetUtil.UTF_8);
    }

    private GenUtils() {}

    public static GenerModel getGenerByProps() {
        GenerModel model = new GenerModel();
        model.setPack(props.getStr("generator.output.dir"));
        model.setDbUrl(props.getStr("generator.db.url"));
        model.setDriverName(props.getStr("generator.db.driverName"));
        model.setUsername(props.getStr("generator.db.username"));
        model.setPassword(props.getStr("generator.db.password"));
        model.setTableName(props.getStr("generator.tables"));
        model.setModules(props.getStr("generator.package.moduleName"));
        model.setPrefix(props.getStr("generator.table.prefix"));
        model.setTemplateGroup(props.getStr("generator.template.group", "curd"));
        model.setParentPackage(props.getStr("generator.package.parent"));
        model.setOutputDirXml(props.getStr("generator.output.dir.xml"));
        model.setEnableSwaggerV3(props.getBool("generator.swagger.v3", true));
        model.setEnableSuperClass(props.getBool("generator.super.enabled", false));
        model.setAuthor(props.getStr("generator.author", "admin"));
        model.setResponseClass(props.getStr("generator.response.class"));
        model.setDtoEnabled(props.getBool("generator.dto.enabled", false));
        model.setVueEnabled(props.getBool("generator.vue.enabled", false));
        model.setMicroserviceEnabled(props.getBool("generator.microservice.enabled", false));
        model.setSuperControllerClass(props.getStr("generator.super.controller"));
        model.setSuperEntityClass(props.getStr("generator.super.entity"));
        model.setSuperServiceClass(props.getStr("generator.super.service"));
        model.setSuperServiceImplClass(props.getStr("generator.super.serviceImpl"));
        model.setSuperEntityColumns(props.getStr("generator.super.entityColumns"));
        return model;
    }

    public static void buildAndExecute(GenerModel model) {
        DataSourceConfig.Builder dsBuilder = new DataSourceConfig.Builder(
                model.getDbUrl(), model.getUsername(), model.getPassword());

        FastAutoGenerator.create(dsBuilder)
                .globalConfig(builder -> {
                    builder.author(model.getAuthor())
                            .outputDir(model.getPack())
                            .commentDate("yyyy-MM-dd HH:mm")
                            .disableOpenDir();
                    if (model.isEnableSwaggerV3()) {
                        builder.enableSwagger();
                    }
                })
                .packageConfig(builder -> {
                        builder.parent(model.getParentPackage())
                                .moduleName(model.getModules() != null ? model.getModules() : "")
                                .entity("entity")
                                .mapper("mapper")
                                .service("service")
                                .serviceImpl("service.impl")
                                .controller("controller");
                        Map<OutputFile, String> pathInfo = new HashMap<>();
                        if (model.getOutputDirXml() != null && !model.getOutputDirXml().isEmpty()) {
                            pathInfo.put(OutputFile.xml, model.getOutputDirXml());
                        }
                        builder.pathInfo(pathInfo);
                    })
                .strategyConfig(builder -> {
                    builder.addInclude(model.getTableName().split(","));

                    if (model.getPrefix() != null && !model.getPrefix().isEmpty()) {
                        builder.addTablePrefix(model.getPrefix().split(","));
                    }

                    builder.controllerBuilder()
                            .enableRestStyle()
                            .enableFileOverride();
                    if ("simple".equals(model.getTemplateGroup()) && model.isEnableSuperClass()
                            && model.getSuperControllerClass() != null) {
                        builder.controllerBuilder().superClass(model.getSuperControllerClass());
                    }

                    builder.mapperBuilder()
                            .enableMapperAnnotation()
                            .enableBaseColumnList()
                            .enableBaseResultMap()
                            .enableFileOverride();

                    builder.entityBuilder()
                            .enableLombok()
                            .enableChainModel()
                            .enableFileOverride()
                            .enableTableFieldAnnotation();
                    if ("simple".equals(model.getTemplateGroup()) && model.isEnableSuperClass()
                            && model.getSuperEntityClass() != null) {
                        builder.entityBuilder().superClass(model.getSuperEntityClass());
                    }
                    if (model.getSuperEntityColumns() != null && !model.getSuperEntityColumns().isEmpty()) {
                        builder.entityBuilder().addSuperEntityColumns(model.getSuperEntityColumns().split(","));
                    }

                    builder.serviceBuilder()
                            .enableFileOverride()
                            .convertServiceFileName(entityName -> entityName + "Service")
                            .convertServiceImplFileName(entityName -> entityName + "ServiceImpl");
                    if ("simple".equals(model.getTemplateGroup()) && model.isEnableSuperClass()
                            && model.getSuperServiceClass() != null) {
                        builder.serviceBuilder().superServiceClass(model.getSuperServiceClass());
                    }
                    if ("simple".equals(model.getTemplateGroup()) && model.isEnableSuperClass()
                            && model.getSuperServiceImplClass() != null) {
                        builder.serviceBuilder().superServiceImplClass(model.getSuperServiceImplClass());
                    }
                })
                .templateConfig(builder -> {
                    String templatePath = "curd".equals(model.getTemplateGroup()) ? "/curd/" : "/simple/";
                    builder.controller(templatePath + "controller.java")
                            .entity(templatePath + "entity.java")
                            .service(templatePath + "service.java")
                            .serviceImpl(templatePath + "serviceImpl.java")
                            .mapper(templatePath + "mapper.java")
                            .xml(templatePath + "mapper.xml");
                })
                .injectionConfig(builder -> {
                    Map<String, Object> customMap = new HashMap<>();
                    customMap.put("swaggerV3", model.isEnableSwaggerV3());
                    customMap.put("swagger2", !model.isEnableSwaggerV3());
                    if (model.getResponseClass() != null && !model.getResponseClass().isEmpty()) {
                        customMap.put("responseClass", model.getResponseClass());
                    }
                    builder.customMap(customMap);

                    List<CustomFile> customFiles = new ArrayList<>();

                    if (model.isDtoEnabled()) {
                        customFiles.add(new CustomFile.Builder()
                                .fileName("InputDTO.java")
                                .templatePath("/dto/input.java.ftl")
                                .packageName("dto.input")
                                .build());
                        customFiles.add(new CustomFile.Builder()
                                .fileName("OutputDTO.java")
                                .templatePath("/dto/output.java.ftl")
                                .packageName("dto.output")
                                .build());
                    }
                    if (model.isVueEnabled()) {
                        customFiles.add(new CustomFile.Builder()
                                .fileName("-manage.vue")
                                .templatePath("/vue/manage-element.vue.ftl")
                                .build());
                        customFiles.add(new CustomFile.Builder()
                                .fileName(".js")
                                .templatePath("/vue/vue.js.ftl")
                                .build());
                    }
                    if (model.isMicroserviceEnabled()) {
                        customFiles.add(new CustomFile.Builder()
                                .fileName("ClientService.java")
                                .templatePath("/service/clientService.java.ftl")
                                .build());
                        customFiles.add(new CustomFile.Builder()
                                .fileName("RemoteService.java")
                                .templatePath("/service/remoteService.java.ftl")
                                .build());
                    }

                    if (!customFiles.isEmpty()) {
                        builder.customFile(customFiles);
                    }
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}