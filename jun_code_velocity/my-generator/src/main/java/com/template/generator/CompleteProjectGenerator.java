package com.template.generator;

import com.template.domain.GenTable;
import com.template.util.VelocityInitializer;
import com.template.util.VelocityUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 完整项目生成器 - 一键生成可运行的前后端项目
 *
 * @author Wujun
 */
@Component
public class CompleteProjectGenerator {

    private final ApiSpecGenerator apiSpecGenerator;

    public CompleteProjectGenerator(ApiSpecGenerator apiSpecGenerator) {
        this.apiSpecGenerator = apiSpecGenerator;
    }

    public byte[] generateCompleteProject(List<GenTable> tables, ProjectConfig config) {
        VelocityInitializer.initVelocity();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        try {
            generateBackendProject(tables, config, zip);
            generateFrontendProject(tables, config, zip);
            generateDeploymentFiles(config, zip);
            generateProjectDocuments(tables, config, zip);
        } catch (Exception e) {
            throw new RuntimeException("生成完整项目失败", e);
        } finally {
            try { zip.close(); } catch (IOException e) { /* ignore */ }
        }

        return outputStream.toByteArray();
    }

    private void generateBackendProject(List<GenTable> tables, ProjectConfig config, ZipOutputStream zip) throws IOException {
        VelocityContext context = createProjectContext(config);
        generateBackendBaseFiles(context, config, zip);
        generateBackendCommonFiles(context, config, zip);

        for (GenTable table : tables) {
            generateBackendBusinessModule(table, context, config, zip);
        }
    }

    private void generateBackendBaseFiles(VelocityContext context, ProjectConfig config, ZipOutputStream zip) throws IOException {
        String backendPath = config.getProjectName() + "-backend/";
        generateFile("vm/project/backend/Application.java.vm",
                backendPath + "src/main/java/" + config.getPackagePath() + "/" + config.getClassName() + "Application.java", context, zip);
        generateFile("vm/project/backend/pom.xml.vm", backendPath + "pom.xml", context, zip);
        generateFile("vm/project/backend/application.yml.vm", backendPath + "src/main/resources/application.yml", context, zip);
        generateFile("vm/project/backend/application-dev.yml.vm", backendPath + "src/main/resources/application-dev.yml", context, zip);
        generateFile("vm/project/backend/application-prod.yml.vm", backendPath + "src/main/resources/application-prod.yml", context, zip);
    }

    private void generateBackendCommonFiles(VelocityContext context, ProjectConfig config, ZipOutputStream zip) throws IOException {
        String backendPath = config.getProjectName() + "-backend/";
        String pkgPath = "src/main/java/" + config.getPackagePath() + "/";
        generateFile("vm/project/backend/common/Result.java.vm", backendPath + pkgPath + "common/Result.java", context, zip);
        generateFile("vm/project/backend/common/PageResult.java.vm", backendPath + pkgPath + "common/PageResult.java", context, zip);
        generateFile("vm/project/backend/common/AjaxResult.java.vm", backendPath + pkgPath + "common/AjaxResult.java", context, zip);
        generateFile("vm/project/backend/common/TableDataInfo.java.vm", backendPath + pkgPath + "common/TableDataInfo.java", context, zip);
        generateFile("vm/project/backend/common/BaseController.java.vm", backendPath + pkgPath + "common/BaseController.java", context, zip);
        generateFile("vm/project/backend/utils/TableSupport.java.vm", backendPath + pkgPath + "utils/TableSupport.java", context, zip);
        generateFile("vm/project/backend/config/GlobalExceptionHandler.java.vm", backendPath + pkgPath + "config/GlobalExceptionHandler.java", context, zip);
        generateFile("vm/project/backend/config/SecurityConfig.java.vm", backendPath + pkgPath + "config/SecurityConfig.java", context, zip);
        generateFile("vm/project/backend/config/SwaggerConfig.java.vm", backendPath + pkgPath + "config/SwaggerConfig.java", context, zip);
        generateFile("vm/project/backend/config/CorsConfig.java.vm", backendPath + pkgPath + "config/CorsConfig.java", context, zip);
        generateFile("vm/project/backend/config/MybatisPlusConfig.java.vm", backendPath + pkgPath + "config/MybatisPlusConfig.java", context, zip);
    }

    private void generateBackendBusinessModule(GenTable table, VelocityContext context, ProjectConfig config, ZipOutputStream zip) throws IOException {
        VelocityContext tableContext = VelocityUtils.prepareContext(table);
        for (Object key : context.getKeys()) {
            tableContext.put((String) key, context.get((String) key));
        }
        tableContext.put("packageName", config.getPackageName() + ".modules.system");
        tableContext.put("moduleName", "system");
        tableContext.put("basePackageName", config.getPackageName());

        Map<String, Object> apiSpec = apiSpecGenerator.generateApiSpec(table);
        tableContext.put("apiSpec", apiSpec);

        String backendPath = config.getProjectName() + "-backend/";
        String pkgPath = "src/main/java/" + config.getPackagePath() + "/";
        String modulePath = pkgPath + "modules/system/";
        String templatePath = "vm/project/backend/";

        generateFile(templatePath + "domain.java.vm", backendPath + modulePath + "domain/" + table.getClassName() + ".java", tableContext, zip);
        generateFile("vm/java/mybatis/java/mapper.java.vm", backendPath + modulePath + "mapper/" + table.getClassName() + "Mapper.java", tableContext, zip);
        generateFile("vm/java/mybatis/xml/mapper.xml.vm", backendPath + "src/main/resources/mapper/system/" + table.getClassName() + "Mapper.xml", tableContext, zip);
        generateFile("vm/java/mybatis/java/service.java.vm", backendPath + modulePath + "service/I" + table.getClassName() + "Service.java", tableContext, zip);
        generateFile(templatePath + "serviceImpl.java.vm", backendPath + modulePath + "service/impl/" + table.getClassName() + "ServiceImpl.java", tableContext, zip);
        generateFile(templatePath + "controller.java.vm", backendPath + modulePath + "controller/" + table.getClassName() + "Controller.java", tableContext, zip);
    }

    private void generateFrontendProject(List<GenTable> tables, ProjectConfig config, ZipOutputStream zip) throws IOException {
        String frontendPath = config.getProjectName() + "-frontend/";
        generateFrontendBaseFiles(config, zip, frontendPath);
        for (GenTable table : tables) {
            generateFrontendBusinessModule(table, config, zip, frontendPath);
        }
        generateFrontendRoutes(tables, config, zip, frontendPath);
    }

    private void generateFrontendBaseFiles(ProjectConfig config, ZipOutputStream zip, String frontendPath) throws IOException {
        VelocityContext context = createProjectContext(config);
        generateFile("vm/project/frontend/package.json.vm", frontendPath + "package.json", context, zip);
        generateFile("vm/project/frontend/vue.config.js.vm", frontendPath + "vue.config.js", context, zip);
        generateFile("vm/project/frontend/main.js.vm", frontendPath + "src/main.js", context, zip);
        generateFile("vm/project/frontend/App.vue.vm", frontendPath + "src/App.vue", context, zip);
        generateFile("vm/project/frontend/utils/request.js.vm", frontendPath + "src/utils/request.js", context, zip);
        generateFile("vm/project/frontend/store.js.vm", frontendPath + "src/store/index.js", context, zip);
        generateFile("vm/project/frontend/Dashboard.vue.vm", frontendPath + "src/views/Dashboard.vue", context, zip);
        generateFile("vm/project/frontend/utils/auth.js.vm", frontendPath + "src/utils/auth.js", context, zip);
        generateFile("vm/project/deployment/nginx.conf.vm", frontendPath + "nginx.conf", context, zip);
    }

    private void generateFrontendBusinessModule(GenTable table, ProjectConfig config, ZipOutputStream zip, String frontendPath) throws IOException {
        VelocityContext context = VelocityUtils.prepareContext(table);
        VelocityContext projectContext = createProjectContext(config);
        for (Object key : projectContext.getKeys()) {
            context.put((String) key, projectContext.get((String) key));
        }
        generateFile("vm/project/frontend/api.js.vm", frontendPath + "src/api/" + table.getBusinessName() + ".js", context, zip);
        generateFile("vm/project/frontend/index-simple.vue.vm", frontendPath + "src/views/" + table.getModuleName() + "/" + table.getBusinessName() + "/index.vue", context, zip);
        generateFile("vm/project/frontend/form.vue.vm", frontendPath + "src/views/" + table.getModuleName() + "/" + table.getBusinessName() + "/form.vue", context, zip);
    }

    private void generateFrontendRoutes(List<GenTable> tables, ProjectConfig config, ZipOutputStream zip, String frontendPath) throws IOException {
        VelocityContext context = createProjectContext(config);
        context.put("tables", tables);
        generateFile("vm/project/frontend/router.js.vm", frontendPath + "src/router/index.js", context, zip);
        generateFile("vm/project/frontend/Layout.vue.vm", frontendPath + "src/layout/Layout.vue", context, zip);
        generateFile("vm/project/frontend/Sidebar.vue.vm", frontendPath + "src/layout/components/Sidebar.vue", context, zip);
    }

    private void generateDeploymentFiles(ProjectConfig config, ZipOutputStream zip) throws IOException {
        VelocityContext context = createProjectContext(config);
        generateFile("vm/project/deployment/Dockerfile-backend.vm", config.getProjectName() + "-backend/Dockerfile", context, zip);
        generateFile("vm/project/deployment/Dockerfile-frontend.vm", config.getProjectName() + "-frontend/Dockerfile", context, zip);
        generateFile("vm/project/deployment/docker-compose.yml.vm", "docker-compose.yml", context, zip);
        generateFile("vm/project/deployment/start.sh.vm", "start.sh", context, zip);
        generateFile("vm/project/deployment/stop.sh.vm", "stop.sh", context, zip);
    }

    private void generateProjectDocuments(List<GenTable> tables, ProjectConfig config, ZipOutputStream zip) throws IOException {
        VelocityContext context = createProjectContext(config);
        context.put("tables", tables);
        generateFile("vm/project/docs/README.md.vm", "README.md", context, zip);
        generateFile("vm/project/docs/API.md.vm", "docs/API.md", context, zip);
    }

    private VelocityContext createProjectContext(ProjectConfig config) {
        VelocityContext context = new VelocityContext();
        context.put("projectName", config.getProjectName());
        context.put("projectComment", config.getProjectComment());
        context.put("packageName", config.getPackageName());
        context.put("packagePath", config.getPackagePath());
        context.put("className", config.getClassName());
        context.put("groupId", config.getGroupId());
        context.put("artifactId", config.getArtifactId());
        context.put("version", config.getVersion());
        context.put("author", config.getAuthor());
        context.put("datetime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        context.put("copyrightYear", String.valueOf(LocalDateTime.now().getYear()));
        context.put("driverClassName", config.getDriverClassName());
        context.put("datasourceUrl", config.getDatasourceUrl());
        context.put("datasourceUsername", config.getDatasourceUsername());
        context.put("datasourcePassword", config.getDatasourcePassword());
        context.put("serverPort", config.getServerPort());
        context.put("jwtSecret", config.getJwtSecret());
        context.put("springBootVersion", "2.7.18");
        context.put("mybatisPlusVersion", "3.5.3.1");
        context.put("druidVersion", "1.2.16");
        context.put("jwtVersion", "0.9.1");
        return context;
    }

    private void generateFile(String templateName, String fileName, VelocityContext context, ZipOutputStream zip) throws IOException {
        Template template = Velocity.getTemplate(templateName, "UTF-8");
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        zip.putNextEntry(new ZipEntry(fileName));
        zip.write(sw.toString().getBytes("UTF-8"));
        zip.closeEntry();
    }

    public static class ProjectConfig {
        private String projectName = "demo-project";
        private String projectComment = "演示项目";
        private String packageName = "com.example.demo";
        private String className = "Demo";
        private String groupId = "com.example";
        private String artifactId = "demo";
        private String version = "1.0.0";
        private String author = "Wujun";
        private String driverClassName = "com.mysql.cj.jdbc.Driver";
        private String datasourceUrl = "jdbc:mysql://localhost:3306/demo";
        private String datasourceUsername = "root";
        private String datasourcePassword = "123456";
        private Integer serverPort = 8080;
        private String jwtSecret = "jun-secret-key";

        public String getProjectName() { return projectName; }
        public void setProjectName(String projectName) { this.projectName = projectName; }
        public String getProjectComment() { return projectComment; }
        public void setProjectComment(String projectComment) { this.projectComment = projectComment; }
        public String getPackageName() { return packageName; }
        public void setPackageName(String packageName) { this.packageName = packageName; }
        public String getPackagePath() { return packageName != null ? packageName.replace(".", "/") : ""; }
        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }
        public String getGroupId() { return groupId; }
        public void setGroupId(String groupId) { this.groupId = groupId; }
        public String getArtifactId() { return artifactId; }
        public void setArtifactId(String artifactId) { this.artifactId = artifactId; }
        public String getVersion() { return version; }
        public void setVersion(String version) { this.version = version; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getDriverClassName() { return driverClassName; }
        public void setDriverClassName(String driverClassName) { this.driverClassName = driverClassName; }
        public String getDatasourceUrl() { return datasourceUrl; }
        public void setDatasourceUrl(String datasourceUrl) { this.datasourceUrl = datasourceUrl; }
        public String getDatasourceUsername() { return datasourceUsername; }
        public void setDatasourceUsername(String datasourceUsername) { this.datasourceUsername = datasourceUsername; }
        public String getDatasourcePassword() { return datasourcePassword; }
        public void setDatasourcePassword(String datasourcePassword) { this.datasourcePassword = datasourcePassword; }
        public Integer getServerPort() { return serverPort; }
        public void setServerPort(Integer serverPort) { this.serverPort = serverPort; }
        public String getJwtSecret() { return jwtSecret; }
        public void setJwtSecret(String jwtSecret) { this.jwtSecret = jwtSecret; }
    }
}