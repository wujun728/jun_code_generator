package com.jeasy;

import com.google.common.collect.Maps;
import com.jeasy.common.charset.CharsetKit;
import com.jeasy.common.file.FileKit;
import com.jeasy.common.file.PathKit;
import com.jeasy.common.str.StrKit;
import com.jeasy.common.template.TemplateKit;
import com.jeasy.conf.Config;
import com.jeasy.db.DbInfo;
import com.jeasy.db.TableInfo;
import com.jeasy.model.PathInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/10/20 15:44
 */
@Slf4j
public class CodeGenMain {

    public static void main(final String[] args) throws Exception {
        Config conf = new Config();
        DbInfo dbInfo = DbInfo.getInstance(conf);
//        genCodeForMvc(conf, dbInfo);
        genCodeForFront(conf, dbInfo);
    }

    private static void genCodeForFront(Config conf, DbInfo dbInfo) {
        Map<String, Object> model = Maps.newHashMap();
        model.put("conf", conf);

        for (TableInfo table : dbInfo.getTables()) {
            model.put("table", table);

            String templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/app/form";
            String templateName = "add.ftl";
            String targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/app" + File.separator + table.getCamelName() + "/form";
            String targetName = "add.vue";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/app/form";
            templateName = "modify.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/app" + File.separator + table.getCamelName() + "/form";
            targetName = "modify.vue";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/app/form";
            templateName = "remove.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/app" + File.separator + table.getCamelName() + "/form";
            targetName = "remove.vue";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/app/form";
            templateName = "show.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/app" + File.separator + table.getCamelName() + "/form";
            targetName = "show.vue";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/app";
            templateName = "index.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/app" + File.separator + table.getCamelName();
            targetName = "index.vue";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/app/i18n";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/app" + File.separator + table.getCamelName() + "/i18n";
            copyDirectory(conf.getAdminFrontName(), templatePath, targetPath);

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front";
            templateName = "models.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/models";
            targetName = table.getCamelName() + ".js";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front";
            templateName = "routes.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/router/app";
            targetName = table.getCamelName() + ".js";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/store";
            templateName = "actions.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/store/modules" + File.separator + table.getCamelName();
            targetName = "actions.js";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/store";
            templateName = "getters.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/store/modules" + File.separator + table.getCamelName();
            targetName = "getters.js";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/store";
            templateName = "index.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/store/modules" + File.separator + table.getCamelName();
            targetName = "index.js";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/store";
            templateName = "mutations.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/store/modules" + File.separator + table.getCamelName();
            targetName = "mutations.js";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));

            templatePath = PathKit.getWebRootPath() + File.separator + "src/main/resources/jeasy-admin-front/store";
            templateName = "types.ftl";
            targetPath = StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + File.separator + "jeasy-admin-front/src/store/modules" + File.separator + table.getCamelName();
            targetName = "types.js";
            executeFreemarker(conf.getAdminFrontName(), model, new PathInfo(templatePath, templateName, targetPath, targetName));
        }
    }

    private static void copyDirectory(String moduleName, String templatePath, String targetPath) {
        log.info("Generate " + moduleName + " Module : " + targetPath);
        FileKit.createDir(targetPath, false);
        log.info("==> success");
        log.info("Generate " + moduleName + " Module : " + targetPath);
        FileKit.copyDirectiory(new File(templatePath), targetPath);
        log.info("==> success");
    }

    private static void executeFreemarker(String moduleName, Map<String, Object> model, PathInfo pathInfo) {
        log.info("Generate " + moduleName + " Module : " + pathInfo.getTargetPath() + File.separator + pathInfo.getTargetName());
        TemplateKit.executeFreemarker(pathInfo.getTemplatePath(), pathInfo.getTemplateName(), CharsetKit.DEFAULT_ENCODE, model, pathInfo.getTargetPath(), pathInfo.getTargetName());
        log.info("==> success");
    }

    private static void genCodeForMvc(final Config conf, final DbInfo dbInfo) throws Exception {
        Map<String, Object> model = Maps.newHashMap();
        model.put("conf", conf);

        log.info("\n--------------------------------------------------------------Generate Dao Module Start--------------------------------------------------------------");
        genDaoModule(conf.getDaoModuleName(), model, PathKit.getWebRootPath() + File.separator + "src/main/resources/dao", StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + "/" + conf.getDaoModuleName(), dbInfo, conf.getBasePackage().replace(".", "/"));
        log.info("\n--------------------------------------------------------------Generate Dao Module End  --------------------------------------------------------------");

        log.info("\n--------------------------------------------------------------Generate Service Module Start--------------------------------------------------------------");
        genServiceModule(conf.getServiceModuleName(), model, PathKit.getWebRootPath() + File.separator + "src/main/resources/service", StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + "/" + conf.getServiceModuleName(), dbInfo, conf.getBasePackage().replace(".", "/"));
        log.info("\n--------------------------------------------------------------Generate Service Module End  --------------------------------------------------------------");

        log.info("\n--------------------------------------------------------------Generate Web Module Start--------------------------------------------------------------");
        genWebModule(conf.getWebModuleName(), model, PathKit.getWebRootPath() + File.separator + "src/main/resources/web", StrKit.subPre(PathKit.getWebRootPath(), PathKit.getWebRootPath().lastIndexOf(File.separator)) + "/" + conf.getWebModuleName(), dbInfo, conf.getBasePackage().replace(".", "/"));
        log.info("\n--------------------------------------------------------------Generate Web Module End  --------------------------------------------------------------");
    }

    private static void genWebModule(final String webModuleName, final Map<String, Object> model, final String templatePath, final String targetPath, final DbInfo dbInfo, final String basePackage) throws IOException {
        log.info("Generate " + webModuleName + " Module : " + targetPath + "/src/test/java");
        FileKit.createDir(targetPath + "/src/test/java", false);
        log.info("==> success");

        log.info("Generate " + webModuleName + " Module : " + targetPath + "/src/main/java");
        FileKit.createDir(targetPath + "/src/main/java", false);
        log.info("==> success");

        log.info("Generate " + webModuleName + " Module : " + targetPath + "/src/main/resources");
        FileKit.createDir(targetPath + "/src/main/resources", false);
        log.info("==> success");

        copyDirectory(webModuleName, templatePath + "/webapp", targetPath + "/src/main/webapp");
        copyDirectory(webModuleName, templatePath + "/resources/dev", targetPath + "/src/main/resources/dev");
        copyDirectory(webModuleName, templatePath + "/resources/qa", targetPath + "/src/main/resources/qa");
        copyDirectory(webModuleName, templatePath + "/resources/prod", targetPath + "/src/main/resources/prod");

        executeFreemarker(webModuleName, model, new PathInfo(templatePath, "pom.ftl", targetPath, "pom.xml"));
        executeFreemarker(webModuleName, model, new PathInfo(templatePath, "web.ftl", targetPath + "/src/main/webapp/WEB-INF", "web.xml"));
        executeFreemarker(webModuleName, model, new PathInfo(templatePath + "/resources", "dispatcher-servlet.ftl", targetPath + "/src/main/resources", "dispatcher-servlet.xml"));
        executeFreemarker(webModuleName, model, new PathInfo(templatePath, "BaseJUnitTester4SpringContext.ftl", targetPath + "/src/test/java" + File.separator + basePackage, "BaseJUnitTester4SpringContext.java"));
        executeFreemarker(webModuleName, model, new PathInfo(templatePath, "IndexTest.ftl", targetPath + "/src/test/java" + File.separator + basePackage, "IndexTest.java"));

        for (TableInfo table : dbInfo.getTables()) {
            model.put("table", table);
            // Controller
            String controllerTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "controller";
            String controllerTargetName = table.getClassName() + "Controller.java";
            executeFreemarker(webModuleName, model, new PathInfo(templatePath, "Controller.ftl", controllerTargetPath, controllerTargetName));
        }
    }

    private static void genDaoModule(final String daoModuleName, final Map<String, Object> model, final String templatePath, final String targetPath, final DbInfo dbInfo, final String basePackage) throws IOException {
        log.info("Generate " + daoModuleName + " Module : " + targetPath + "/src/test/java");
        FileKit.createDir(targetPath + "/src/test/java", false);
        log.info("==> success");

        log.info("Generate " + daoModuleName + " Module : " + targetPath + "/src/main/java");
        FileKit.createDir(targetPath + "/src/main/java", false);
        log.info("==> success");

        log.info("Generate " + daoModuleName + " Module : " + targetPath + "/src/main/resources");
        FileKit.createDir(targetPath + "/src/main/resources", false);
        log.info("==> success");

        executeFreemarker(daoModuleName, model, new PathInfo(templatePath, "pom.ftl", targetPath, "pom.xml"));
        executeFreemarker(daoModuleName, model, new PathInfo(templatePath + "/resources", "sqlMapConfig.ftl", targetPath + "/src/main/resources", "sqlMapConfig.xml"));
        executeFreemarker(daoModuleName, model, new PathInfo(templatePath + "/resources", "applicationContext-dao.ftl", targetPath + "/src/main/resources", "applicationContext-dao.xml"));

        for (TableInfo table : dbInfo.getTables()) {
            model.put("table", table);

            // Model
            String modelTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "entity";
            String modelTargetName = table.getClassName() + "Entity.java";
            executeFreemarker(daoModuleName, model, new PathInfo(templatePath, "Entity.ftl", modelTargetPath, modelTargetName));

            // DAO
            String daoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dao";
            String daoTargetName = table.getClassName() + "DAO.java";
            executeFreemarker(daoModuleName, model, new PathInfo(templatePath, "DAO.ftl", daoTargetPath, daoTargetName));

            // Mapper
            String mapperTargetPath = targetPath + "/src/main/resources/sqlMapper";
            String mapperTargetName = table.getClassName() + "Mapper.xml";
            executeFreemarker(daoModuleName, model, new PathInfo(templatePath, "Mapper.ftl", mapperTargetPath, mapperTargetName));
        }
    }

    private static void genServiceModule(final String serviceModuleName, final Map<String, Object> model, final String templatePath, final String targetPath, final DbInfo dbInfo, final String basePackage) throws IOException {
        log.info("Generate " + serviceModuleName + " Module : " + targetPath + "/src/test/java");
        FileKit.createDir(targetPath + "/src/test/java", false);
        log.info("==> success");

        log.info("Generate " + serviceModuleName + " Module : " + targetPath + "/src/main/java");
        FileKit.createDir(targetPath + "/src/main/java", false);
        log.info("==> success");

        log.info("Generate " + serviceModuleName + " Module : " + targetPath + "/src/main/resources");
        FileKit.createDir(targetPath + "/src/main/resources", false);
        log.info("==> success");

        executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "pom.ftl", targetPath, "pom.xml"));
        executeFreemarker(serviceModuleName, model, new PathInfo(templatePath + "/resources", "applicationContext-server.ftl", targetPath + "/src/main/resources", "applicationContext-server.xml"));
        executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "BaseJUnitTester4SpringContext.ftl", targetPath + "/src/test/java" + File.separator + basePackage, "BaseJUnitTester4SpringContext.java"));

        for (TableInfo table : dbInfo.getTables()) {
            model.put("table", table);

            // Model
            String modelTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "biz/model";
            String modelTargetName = table.getClassName() + "Model.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "Model.ftl", modelTargetPath, modelTargetName));

            // Biz
            String bizTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "biz";
            String bizTargetName = table.getClassName() + "Biz.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "Biz.ftl", bizTargetPath, bizTargetName));

            // JUnit
            String junitTargetPath = targetPath + "/src/test/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "biz";
            String junitTargetName = table.getClassName() + "BizJUnitTest.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "BizJUnitTest.ftl", junitTargetPath, junitTargetName));

            // Service
            String serviceTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "service";
            String serviceTargetName = table.getClassName() + "Service.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "Service.ftl", serviceTargetPath, serviceTargetName));

            // Service Impl
            String serviceImplTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "service/impl";
            String serviceImplTargetName = table.getClassName() + "ServiceImpl.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "ServiceImpl.ftl", serviceImplTargetPath, serviceImplTargetName));

            // Manager
            String managerTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "manager";
            String managerTargetName = table.getClassName() + "Manager.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "Manager.ftl", managerTargetPath, managerTargetName));

            // DTO
            String dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            String dtoTargetName = table.getClassName() + "AddReqDTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "AddReqDTO.ftl", dtoTargetPath, dtoTargetName));

            dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            dtoTargetName = table.getClassName() + "ListReqDTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "ListReqDTO.ftl", dtoTargetPath, dtoTargetName));

            dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            dtoTargetName = table.getClassName() + "ListResDTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "ListResDTO.ftl", dtoTargetPath, dtoTargetName));

            dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            dtoTargetName = table.getClassName() + "ModifyReqDTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "ModifyReqDTO.ftl", dtoTargetPath, dtoTargetName));

            dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            dtoTargetName = table.getClassName() + "PageReqDTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "PageReqDTO.ftl", dtoTargetPath, dtoTargetName));

            dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            dtoTargetName = table.getClassName() + "PageResDTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "PageResDTO.ftl", dtoTargetPath, dtoTargetName));

            dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            dtoTargetName = table.getClassName() + "RemoveReqDTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "RemoveReqDTO.ftl", dtoTargetPath, dtoTargetName));

            dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            dtoTargetName = table.getClassName() + "ShowResDTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "ShowResDTO.ftl", dtoTargetPath, dtoTargetName));

            dtoTargetPath = targetPath + "/src/main/java" + File.separator + basePackage + File.separator + table.getLowerCamelName() + File.separator + "dto";
            dtoTargetName = table.getClassName() + "DTO.java";
            executeFreemarker(serviceModuleName, model, new PathInfo(templatePath, "DTO.ftl", dtoTargetPath, dtoTargetName));
        }
    }
}
