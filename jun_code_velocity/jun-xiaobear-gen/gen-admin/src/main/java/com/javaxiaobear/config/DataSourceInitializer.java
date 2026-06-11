package com.javaxiaobear.config;

import com.javaxiaobear.domain.DynamicDataSource;
import com.javaxiaobear.service.IDynamicDataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 数据源初始化器
 * 在系统启动时检查是否存在数据源配置，如果没有则自动创建默认配置
 * 
 * @author javaxiaobear
 * @date 2025-01-08
 */
@Component
public class DataSourceInitializer implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceInitializer.class);

    @Autowired
    private IDynamicDataSourceService dynamicDataSourceService;

    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/xiaobear-gen?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&autoReconnect=true}")
    private String defaultUrl;

    @Value("${spring.datasource.username:root}")
    private String defaultUsername;

    @Value("${spring.datasource.password:123456}")
    private String defaultPassword;

    @Value("${spring.datasource.driver-class-name:com.mysql.cj.jdbc.Driver}")
    private String defaultDriverClassName;

    // 标记是否已经成功初始化
    private final AtomicBoolean initialized = new AtomicBoolean(false);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 延迟一点时间确保数据库连接已经建立
            Thread.sleep(1000);
            initializeDefaultDataSource();
        } catch (Exception e) {
            logger.error("初始化默认数据源失败: {}", e.getMessage(), e);
            // 不抛出异常，避免影响系统启动
        }
    }

    /**
     * 定时重试初始化（每30秒检查一次，最多重试10次）
     */
    @Scheduled(fixedDelay = 30000, initialDelay = 30000)
    public void retryInitialization() {
        if (!initialized.get()) {
            try {
                initializeDefaultDataSource();
            } catch (Exception e) {
                logger.debug("定时重试初始化默认数据源失败: {}", e.getMessage());
            }
        }
    }

    /**
     * 初始化默认数据源
     */
    private void initializeDefaultDataSource() {
        logger.info("开始检查默认数据源配置...");

        try {
            // 检查是否已存在启用的数据源配置
            if (dynamicDataSourceService.hasEnabledDataSource()) {
                logger.info("已存在启用的数据源配置，跳过初始化");
                return;
            }

            // 检查是否已存在名为"default"的数据源配置
            DynamicDataSource existingDefault = dynamicDataSourceService.selectDynamicDataSourceByName("default");
            if (existingDefault != null) {
                logger.info("已存在名为'default'的数据源配置，跳过创建");
                return;
            }

            // 创建默认数据源配置
            DynamicDataSource defaultDataSource = createDefaultDataSourceConfig();
            
            try {
                int result = dynamicDataSourceService.insertDynamicDataSource(defaultDataSource);
                if (result > 0) {
                    logger.info("成功创建默认数据源配置: {}", defaultDataSource.getName());
                    initialized.set(true); // 标记为已初始化
                } else {
                    logger.warn("创建默认数据源配置失败");
                }
            } catch (Exception e) {
                logger.error("创建默认数据源配置时发生异常: {}", e.getMessage(), e);
            }
        } catch (Exception e) {
            // 如果数据库连接失败，说明可能是首次启动或数据库配置问题
            logger.warn("无法连接到数据库进行数据源配置检查，这可能是首次启动: {}", e.getMessage());
            logger.info("请确保数据库已创建并且配置正确，系统将使用默认配置继续启动");
            logger.info("系统将定期重试初始化数据源配置");
        }
    }

    /**
     * 创建默认数据源配置对象
     * 从 application.yml 配置中读取，避免与 DatabaseSourceConfig 重复配置
     */
    private DynamicDataSource createDefaultDataSourceConfig() {
        DynamicDataSource dataSource = new DynamicDataSource();
        
        // 解析URL获取主机、端口和数据库名
        String[] urlParts = parseJdbcUrl(defaultUrl);
        String host = urlParts[0];
        String port = urlParts[1];
        String dbName = urlParts[2];

        dataSource.setName("default");
        dataSource.setUrl(host);
        dataSource.setPort(Long.valueOf(port));
        dataSource.setDbName(dbName);
        dataSource.setUsername(defaultUsername);
        dataSource.setPassword(defaultPassword);
        dataSource.setDriverClassName(defaultDriverClassName);
        dataSource.setType(getDatabaseType(defaultDriverClassName));
        dataSource.setStatus(1); // 启用状态
        dataSource.setCreateTime(new Date());

        logger.info("从 application.yml 配置创建默认数据源: {}:{}/{}", host, port, dbName);
        return dataSource;
    }

    /**
     * 解析JDBC URL获取主机、端口和数据库名
     * 
     * @param jdbcUrl JDBC连接URL
     * @return [host, port, dbName]
     */
    private String[] parseJdbcUrl(String jdbcUrl) {
        try {
            // 示例: jdbc:mysql://localhost:3306/xiaobear-gen?...
            String[] parts = jdbcUrl.split("://")[1].split("\\?")[0].split("/");
            String hostPort = parts[0];
            String dbName = parts.length > 1 ? parts[1] : "test";
            
            String[] hostPortParts = hostPort.split(":");
            String host = hostPortParts[0];
            String port = hostPortParts.length > 1 ? hostPortParts[1] : "3306";
            
            return new String[]{host, port, dbName};
        } catch (Exception e) {
            logger.warn("解析JDBC URL失败，使用默认值: {}", e.getMessage());
            return new String[]{"localhost", "3306", "test"};
        }
    }

    /**
     * 根据驱动类名获取数据库类型
     * 
     * @param driverClassName 驱动类名
     * @return 数据库类型编码
     */
    private String getDatabaseType(String driverClassName) {
        if (driverClassName.contains("mysql")) {
            return "0"; // MySQL
        } else if (driverClassName.contains("oracle")) {
            return "1"; // Oracle
        } else if (driverClassName.contains("sqlserver")) {
            return "2"; // SQL Server
        } else if (driverClassName.contains("postgresql")) {
            return "3"; // PostgreSQL
        } else if (driverClassName.contains("clickhouse")) {
            return "4"; // ClickHouse
        } else if (driverClassName.contains("sqlite")) {
            return "5"; // SQLite
        } else if (driverClassName.contains("db2")) {
            return "6"; // DB2
        } else if (driverClassName.contains("dm")) {
            return "7"; // 达梦
        } else {
            return "0"; // 默认为MySQL
        }
    }
}