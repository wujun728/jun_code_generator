package com.javaxiaobear.generator;

import com.javaxiaobear.domain.GenTable;
import com.javaxiaobear.util.VelocityInitializer;
import com.javaxiaobear.util.VelocityUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 完整项目生成器
 * 生成可直接运行的前后端项目
 * 
 * @author javaxiaobear
 * @version 2.0
 * @since 2024-01-01
 */
@Component
public class CompleteProjectGenerator {
    
    private final ApiSpecGenerator apiSpecGenerator;
    
    public CompleteProjectGenerator(ApiSpecGenerator apiSpecGenerator) {
        this.apiSpecGenerator = apiSpecGenerator;
    }
    
    /**
     * 生成完整项目
     */
    public byte[] generateCompleteProject(List<GenTable> tables, ProjectConfig config) {
        VelocityInitializer.initVelocity();
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        
        try {
            // 生成后端项目
            generateBackendProject(tables, config, zip);
            
            // 生成前端项目
            generateFrontendProject(tables, config, zip);
            
            // 生成部署文件
            generateDeploymentFiles(config, zip);
            
            // 生成项目文档
            generateProjectDocuments(tables, config, zip);
            
        } catch (Exception e) {
            throw new RuntimeException("生成完整项目失败", e);
        } finally {
            try {
                zip.close();
            } catch (IOException e) {
                // ignore
            }
        }
        
        return outputStream.toByteArray();
    }
    
    /**
     * 生成后端项目
     */
    private void generateBackendProject(List<GenTable> tables, ProjectConfig config, ZipOutputStream zip) throws IOException {
        VelocityContext context = createProjectContext(config);
        
        // 生成项目基础文件
        generateBackendBaseFiles(context, config, zip);
        
        // 生成通用组件
        generateBackendCommonFiles(context, config, zip);
        
        // 生成业务模块
        for (GenTable table : tables) {
            generateBackendBusinessModule(table, context, config, zip);
        }
    }
    
    /**
     * 生成后端基础文件
     */
    private void generateBackendBaseFiles(VelocityContext context, ProjectConfig config, ZipOutputStream zip) throws IOException {
        String backendPath = config.getProjectName() + "-backend/";
        
        // 生成启动类
        generateFile("vm/project/backend/Application.java.vm", 
                    backendPath + "src/main/java/" + config.getPackagePath() + "/" + config.getClassName() + "Application.java",
                    context, zip);
        
        // 生成pom.xml
        generateFile("vm/project/backend/pom.xml.vm", 
                    backendPath + "pom.xml",
                    context, zip);
        
        // 生成配置文件
        generateFile("vm/project/backend/application.yml.vm", 
                    backendPath + "src/main/resources/application.yml",
                    context, zip);
        
        // 生成开发环境配置
        generateFile("vm/project/backend/application-dev.yml.vm", 
                    backendPath + "src/main/resources/application-dev.yml",
                    context, zip);
        
        // 生成生产环境配置
        generateFile("vm/project/backend/application-prod.yml.vm", 
                    backendPath + "src/main/resources/application-prod.yml",
                    context, zip);
    }
    
    /**
     * 生成后端通用组件
     */
    private void generateBackendCommonFiles(VelocityContext context, ProjectConfig config, ZipOutputStream zip) throws IOException {
        String backendPath = config.getProjectName() + "-backend/";
        String packagePath = "src/main/java/" + config.getPackagePath() + "/";
        
        // 生成通用返回结果类
        generateFile("vm/project/backend/common/Result.java.vm", 
                    backendPath + packagePath + "common/Result.java",
                    context, zip);
        
        // 生成分页结果类
        generateFile("vm/project/backend/common/PageResult.java.vm",
                    backendPath + packagePath + "common/PageResult.java",
                    context, zip);

        // 生成Ajax返回结果类
        generateFile("vm/project/backend/common/AjaxResult.java.vm",
                    backendPath + packagePath + "common/AjaxResult.java",
                    context, zip);

        // 生成表格分页数据类
        generateFile("vm/project/backend/common/TableDataInfo.java.vm",
                    backendPath + packagePath + "common/TableDataInfo.java",
                    context, zip);

        // 生成基础控制器
        generateFile("vm/project/backend/common/BaseController.java.vm",
                    backendPath + packagePath + "common/BaseController.java",
                    context, zip);

        // 生成分页工具类
        generateFile("vm/project/backend/utils/TableSupport.java.vm",
                    backendPath + packagePath + "utils/TableSupport.java",
                    context, zip);
        
        // 生成全局异常处理器
        generateFile("vm/project/backend/config/GlobalExceptionHandler.java.vm", 
                    backendPath + packagePath + "config/GlobalExceptionHandler.java",
                    context, zip);
        
        // 生成安全配置
        generateFile("vm/project/backend/config/SecurityConfig.java.vm", 
                    backendPath + packagePath + "config/SecurityConfig.java",
                    context, zip);
        
        // 生成Swagger配置
        generateFile("vm/project/backend/config/SwaggerConfig.java.vm",
                    backendPath + packagePath + "config/SwaggerConfig.java",
                    context, zip);

        // 生成跨域配置
        generateFile("vm/project/backend/config/CorsConfig.java.vm",
                    backendPath + packagePath + "config/CorsConfig.java",
                    context, zip);

        // 生成MyBatis Plus配置
        generateFile("vm/project/backend/config/MybatisPlusConfig.java.vm",
                    backendPath + packagePath + "config/MybatisPlusConfig.java",
                    context, zip);
    }
    
    /**
     * 生成后端业务模块
     */
    private void generateBackendBusinessModule(GenTable table, VelocityContext context, ProjectConfig config, ZipOutputStream zip) throws IOException {
        // 设置表相关的上下文变量
        VelocityContext tableContext = VelocityUtils.prepareContext(table);
        // 手动复制context中的变量到tableContext
        for (Object key : context.getKeys()) {
            tableContext.put((String) key, context.get((String) key));
        }

        // 修复包名问题：使用项目的包名而不是javaxiaobear
        tableContext.put("packageName", config.getPackageName() + ".modules.system");
        tableContext.put("moduleName", "system");

        // 修复导入包名问题 - 添加项目基础包名用于模板中的导入
        tableContext.put("basePackageName", config.getPackageName());
        
        // 生成API规范
        Map<String, Object> apiSpec = apiSpecGenerator.generateApiSpec(table);
        tableContext.put("apiSpec", apiSpec);
        
        String backendPath = config.getProjectName() + "-backend/";
        String packagePath = "src/main/java/" + config.getPackagePath() + "/";
        // 使用system作为模块名，而不是table.getModuleName()
        String modulePath = packagePath + "modules/system/";
        
        // 使用项目专用的模板生成业务代码，默认使用mybatis配置
        String templatePath = "vm/project/backend/";
        String xmlTemplatePath = "vm/java/mybatis/xml/";

        // 生成实体类
        generateFile(templatePath + "domain.java.vm",
                    backendPath + modulePath + "domain/" + table.getClassName() + ".java",
                    tableContext, zip);

        // 生成Mapper接口
        generateFile(templatePath + "mapper.java.vm",
                    backendPath + modulePath + "mapper/" + table.getClassName() + "Mapper.java",
                    tableContext, zip);

        // 生成Mapper XML
        generateFile(xmlTemplatePath + "mapper.xml.vm",
                    backendPath + "src/main/resources/mapper/system/" + table.getClassName() + "Mapper.xml",
                    tableContext, zip);

        // 生成Service接口
        generateFile(templatePath + "service.java.vm",
                    backendPath + modulePath + "service/I" + table.getClassName() + "Service.java",
                    tableContext, zip);

        // 生成Service实现
        generateFile(templatePath + "ServiceImpl.java.vm",
                    backendPath + modulePath + "service/impl/" + table.getClassName() + "ServiceImpl.java",
                    tableContext, zip);

        // 生成Controller（使用现有的完整模板）
        generateFile(templatePath + "controller.java.vm",
                    backendPath + modulePath + "controller/" + table.getClassName() + "Controller.java",
                    tableContext, zip);
    }
    
    /**
     * 生成前端项目
     */
    private void generateFrontendProject(List<GenTable> tables, ProjectConfig config, ZipOutputStream zip) throws IOException {
        String frontendPath = config.getProjectName() + "-frontend/";
        
        // 生成前端基础文件
        generateFrontendBaseFiles(config, zip, frontendPath);

        // 生成业务页面
        for (GenTable table : tables) {
            generateFrontendBusinessModule(table, config, zip, frontendPath);
        }

        // 生成路由配置文件
        generateFrontendRoutes(tables, config, zip, frontendPath);
    }
    
    /**
     * 生成前端基础文件
     */
    private void generateFrontendBaseFiles(ProjectConfig config, ZipOutputStream zip, String frontendPath) throws IOException {
        VelocityContext context = createProjectContext(config);
        
        // 生成package.json
        generateFile("vm/project/frontend/package.json.vm", 
                    frontendPath + "package.json",
                    context, zip);
        
        // 生成vue.config.js
        generateFile("vm/project/frontend/vue.config.js.vm", 
                    frontendPath + "vue.config.js",
                    context, zip);
        
        // 生成main.js
        generateFile("vm/project/frontend/main.js.vm", 
                    frontendPath + "src/main.js",
                    context, zip);
        
        // 生成App.vue
        generateFile("vm/project/frontend/App.vue.vm", 
                    frontendPath + "src/App.vue",
                    context, zip);
        
        // 路由配置将在 generateFrontendRoutes 中生成

        // 生成请求工具
        generateFile("vm/project/frontend/utils/request.js.vm",
                    frontendPath + "src/utils/request.js",
                    context, zip);

        // 生成状态管理
        generateFile("vm/project/frontend/store.js.vm",
                    frontendPath + "src/store/index.js",
                    context, zip);

        // 生成Dashboard页面
        generateFile("vm/project/frontend/Dashboard.vue.vm",
                    frontendPath + "src/views/Dashboard.vue",
                    context, zip);

        // 生成认证工具
        generateFile("vm/project/frontend/utils/auth.js.vm",
                    frontendPath + "src/utils/auth.js",
                    context, zip);

        // 生成nginx配置
        generateFile("vm/project/deployment/nginx.conf.vm",
                    frontendPath + "nginx.conf",
                    context, zip);
    }
    
    /**
     * 生成前端业务模块
     */
    private void generateFrontendBusinessModule(GenTable table, ProjectConfig config, ZipOutputStream zip, String frontendPath) throws IOException {
        VelocityContext context = VelocityUtils.prepareContext(table);
        VelocityContext projectContext = createProjectContext(config);
        // 手动复制projectContext中的变量到context
        for (Object key : projectContext.getKeys()) {
            context.put((String) key, projectContext.get((String) key));
        }
        
        // 生成API文件
        generateFile("vm/project/frontend/api.js.vm",
                    frontendPath + "src/api/" + table.getBusinessName() + ".js",
                    context, zip);

        // 生成页面组件
        generateFile("vm/project/frontend/index-simple.vue.vm",
                    frontendPath + "src/views/" + table.getModuleName() + "/" + table.getBusinessName() + "/index.vue",
                    context, zip);

        // 生成表单组件
        generateFile("vm/project/frontend/form.vue.vm",
                    frontendPath + "src/views/" + table.getModuleName() + "/" + table.getBusinessName() + "/form.vue",
                    context, zip);
    }

    /**
     * 生成前端路由配置
     */
    private void generateFrontendRoutes(List<GenTable> tables, ProjectConfig config, ZipOutputStream zip, String frontendPath) throws IOException {
        VelocityContext context = createProjectContext(config);
        context.put("tables", tables);

        // 生成路由配置文件
        generateFile("vm/project/frontend/router.js.vm",
                    frontendPath + "src/router/index.js",
                    context, zip);

        // 生成布局组件
        generateFile("vm/project/frontend/Layout.vue.vm",
                    frontendPath + "src/layout/Layout.vue",
                    context, zip);

        // 生成导航菜单组件
        generateFile("vm/project/frontend/Sidebar.vue.vm",
                    frontendPath + "src/layout/components/Sidebar.vue",
                    context, zip);
    }

    /**
     * 生成部署文件
     */
    private void generateDeploymentFiles(ProjectConfig config, ZipOutputStream zip) throws IOException {
        VelocityContext context = createProjectContext(config);
        
        // 生成Docker文件
        generateFile("vm/project/deployment/Dockerfile-backend.vm", 
                    config.getProjectName() + "-backend/Dockerfile",
                    context, zip);
        
        generateFile("vm/project/deployment/Dockerfile-frontend.vm", 
                    config.getProjectName() + "-frontend/Dockerfile",
                    context, zip);
        
        // 生成docker-compose.yml
        generateFile("vm/project/deployment/docker-compose.yml.vm",
                    "docker-compose.yml",
                    context, zip);

        // 生成启动脚本
        generateFile("vm/project/deployment/start.sh.vm",
                    "start.sh",
                    context, zip);

        // 生成停止脚本
        generateFile("vm/project/deployment/stop.sh.vm",
                    "stop.sh",
                    context, zip);
    }
    
    /**
     * 生成项目文档
     */
    private void generateProjectDocuments(List<GenTable> tables, ProjectConfig config, ZipOutputStream zip) throws IOException {
        VelocityContext context = createProjectContext(config);
        context.put("tables", tables);
        
        // 生成README.md
        generateFile("vm/project/docs/README.md.vm", 
                    "README.md",
                    context, zip);
        
        // 生成API文档
        generateFile("vm/project/docs/API.md.vm", 
                    "docs/API.md",
                    context, zip);
    }
    
    /**
     * 创建项目上下文
     */
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
        
        // 数据库配置
        context.put("driverClassName", config.getDriverClassName());
        context.put("datasourceUrl", config.getDatasourceUrl());
        context.put("datasourceUsername", config.getDatasourceUsername());
        context.put("datasourcePassword", config.getDatasourcePassword());
        
        // 服务配置
        context.put("serverPort", config.getServerPort());
        context.put("jwtSecret", config.getJwtSecret());

        // 框架版本配置
        context.put("springBootVersion", "2.7.18");
        context.put("mybatisPlusVersion", "3.5.3.1");
        context.put("druidVersion", "1.2.16");
        context.put("jwtVersion", "0.9.1");

        return context;
    }
    
    /**
     * 生成文件
     */
    private void generateFile(String templateName, String fileName, VelocityContext context, ZipOutputStream zip) throws IOException {
        Template template = Velocity.getTemplate(templateName, "UTF-8");
        StringWriter sw = new StringWriter();
        template.merge(context, sw);
        
        try {
            zip.putNextEntry(new ZipEntry(fileName));
            zip.write(sw.toString().getBytes("UTF-8"));
            zip.closeEntry();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("文件编码错误", e);
        }
    }
    
    /**
     * 项目配置类
     */
    public static class ProjectConfig {
        private String projectName;
        private String projectComment;
        private String packageName;
        private String className;
        private String groupId;
        private String artifactId;
        private String version = "1.0.0";
        private String author = "javaxiaobear";
        
        // 数据库配置
        private String driverClassName;
        private String datasourceUrl;
        private String datasourceUsername;
        private String datasourcePassword;
        
        // 服务配置
        private Integer serverPort = 8080;
        private String jwtSecret = "javaxiaobear-secret-key";
        
        // Getters and Setters
        public String getProjectName() { return projectName; }
        public void setProjectName(String projectName) { this.projectName = projectName; }
        
        public String getProjectComment() { return projectComment; }
        public void setProjectComment(String projectComment) { this.projectComment = projectComment; }
        
        public String getPackageName() { return packageName; }
        public void setPackageName(String packageName) { this.packageName = packageName; }
        
        public String getPackagePath() { 
            return packageName != null ? packageName.replace(".", "/") : ""; 
        }
        
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
