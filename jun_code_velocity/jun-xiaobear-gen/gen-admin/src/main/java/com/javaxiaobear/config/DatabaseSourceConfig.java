package com.javaxiaobear.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.javaxiaobear.domain.DynamicDataSource;
import com.javaxiaobear.service.IDynamicDataSourceService;
import com.javaxiaobear.util.StringUtils;
import com.javaxiaobear.util.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;

/**
 * @author 小熊学Java
 * @version 1.0
 * @description: TODO
 * @date 2025/1/8 16:16
 */
@Configuration
public class DatabaseSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSourceConfig.class);

    /**
     * 当前使用的数据源ID  // volatile 保证多线程可见性
     */
    private static volatile Long currentDataSourceId;

    // 从 application.yml 读取配置，与 DataSourceInitializer 保持一致
    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/xiaobear-gen?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&autoReconnect=true}")
    private String defaultUrl;

    @Value("${spring.datasource.username:root}")
    private String defaultUsername;

    @Value("${spring.datasource.password:123456}")
    private String defaultPassword;

    @Value("${spring.datasource.driver-class-name:com.mysql.cj.jdbc.Driver}")
    private String defaultDriverClassName;

    // 移除直接依赖，使用SpringUtils延迟获取

    /**
     * 获取当前数据源ID
     */
    public static Long getCurrentDataSourceId() {
        return currentDataSourceId;
    }

    /**
     * 设置当前数据源ID
     */
    public static void setCurrentDataSourceId(Long id) {
        currentDataSourceId = id;
        logger.debug("切换到数据源ID: {}", id);
    }

    /**
     * 清除当前数据源ID
     */
    public static void clearCurrentDataSourceId() {
        currentDataSourceId = null;
        logger.debug("清除数据源设置，使用默认数据源");
    }

    /**
     * 主数据源配置 - 系统核心功能使用
     */
    @Bean
    @Primary
    public DataSource dataSource() {
        // 直接返回默认数据源，不使用动态路由
        return createDefaultDataSource();
    }



    /**
     * 创建默认数据源
     * 从 application.yml 读取配置，与 DataSourceInitializer 保持一致
     */
    public DruidDataSource createDefaultDataSource() {
        DruidDataSource dataSource = new DruidDataSource();

        // 使用从 application.yml 读取的配置
        dataSource.setUrl(defaultUrl);
        dataSource.setUsername(defaultUsername);
        dataSource.setPassword(defaultPassword);
        dataSource.setDriverClassName(defaultDriverClassName);

        logger.info("创建默认数据源，使用配置: {}", defaultUrl);

        // 连接池配置
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxWait(60 * 1000);
        dataSource.setMaxActive(20);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);

        return dataSource;
    }



    /**
     * 获取数据源服务（延迟加载，避免循环依赖）
     */
    private IDynamicDataSourceService getDynamicDataSourceService() {
        try {
            return SpringUtils.getBean(IDynamicDataSourceService.class);
        } catch (Exception e) {
            logger.warn("无法获取DynamicDataSourceService，可能是循环依赖导致的: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 默认数据源配置
     */
    public DynamicDataSource getDefaultDynamicDataSource() {
        try {
            IDynamicDataSourceService service = getDynamicDataSourceService();
            if (service != null) {
                List<DynamicDataSource> dynamicDataSources = service.selectDynamicDataSourceList(new DynamicDataSource());
                if (!StringUtils.isEmpty(dynamicDataSources)) {
                    return dynamicDataSources.stream().filter(item -> item.getStatus() == 1).findFirst().orElse(null);
                }
            }
        } catch (Exception e) {
            logger.warn("获取默认数据源配置失败: {}", e.getMessage());
        }
        return null;
    }

    /**
     * 根据数据源配置获取对应的DataSource
     */
    public DataSource getDataSourceByConfig(DynamicDataSource config) {
        if (config == null) {
            return null;
        }

        String type = config.getType();
        switch (type) {
            case "0": // MySQL
                return getMySqlDataSource(config);
            case "1": // Oracle
                return getOracleDataSource(config);
            case "2": // SQL Server
                return getSqlServerDataSource(config);
            case "3": // PostgreSQL
                return getPostgreSqlDataSource(config);
            case "4": // ClickHouse
                return getClickHouseDataSource(config);
            case "5": // SQLite
                return getSqliteDataSource(config);
            case "6": // DB2
                return getDb2DataSource(config);
            case "7": // 达梦
                return getDmDataSource(config);
            default:
                return createDataSourceFromConfig(config);
        }
    }

    protected DataSource getClickHouseDataSource(DynamicDataSource config) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:clickhouse://" + config.getUrl() + ":" + config.getPort() + "/" + config.getDbName());
        dataSource.setDriverClassName("com.clickhouse.jdbc.ClickHouseDriver");
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(60 * 1000);
        dataSource.setMaxActive(20);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);
        return dataSource;
    }

    /**
     * 创建通用的Druid数据源
     */
    private DruidDataSource createDruidDataSource(String url, String driverClassName, String username, String password) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        // 通用连接池配置
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(5);
        dataSource.setMaxWait(60 * 1000);
        dataSource.setMaxActive(20);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);

        // 配置监控统计拦截的filters
        try {
            dataSource.setFilters("stat,wall,slf4j");
        } catch (Exception e) {
            System.err.println("设置Druid过滤器失败: " + e.getMessage());
        }

        return dataSource;
    }

    protected DataSource getMySqlDataSource(DynamicDataSource config) {
        String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
                config.getUrl(), config.getPort(), config.getDbName());
        return createDruidDataSource(url, "com.mysql.cj.jdbc.Driver", config.getUsername(), config.getPassword());
    }

    protected DataSource getPostgreSqlDataSource(DynamicDataSource config) {
        String url = String.format("jdbc:postgresql://%s:%s/%s?currentSchema=public",
                config.getUrl(), config.getPort(), config.getDbName());
        return createDruidDataSource(url, "org.postgresql.Driver", config.getUsername(), config.getPassword());
    }

    protected DataSource getSqliteDataSource(DynamicDataSource config) {
        String url = "jdbc:sqlite:" + config.getDbName();
        // SQLite通常不需要用户名和密码
        String username = (config.getUsername() != null && !config.getUsername().isEmpty()) ? config.getUsername() : "";
        String password = (config.getPassword() != null && !config.getPassword().isEmpty()) ? config.getPassword() : "";
        return createDruidDataSource(url, "org.sqlite.JDBC", username, password);
    }

    protected DataSource getDmDataSource(DynamicDataSource config) {
        String url = String.format("jdbc:dm://%s:%s/%s", config.getUrl(), config.getPort(), config.getDbName());
        return createDruidDataSource(url, "dm.jdbc.driver.DmDriver", config.getUsername(), config.getPassword());
    }

    protected DataSource getSqlServerDataSource(DynamicDataSource config) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:sqlserver://" + config.getUrl() + ":" + config.getPort() + ";databaseName=" + config.getDbName() + ";encrypt=false");
        dataSource.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(60 * 1000);
        dataSource.setMaxActive(20);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);
        return dataSource;
    }

    protected DataSource getOracleDataSource(DynamicDataSource config) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:oracle:thin:@//" + config.getUrl() + ":" + config.getPort() + "/" + config.getDbName());
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(60 * 1000);
        dataSource.setMaxActive(20);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);
        return dataSource;
    }

    protected DataSource getDb2DataSource(DynamicDataSource config) {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl("jdbc:db2://" + config.getUrl() + ":" + config.getPort() + "/" + config.getDbName() +":currentSchema=ADMINISTRATOR;");
        dataSource.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
        dataSource.setUsername(config.getUsername());
        dataSource.setPassword(config.getPassword());
        dataSource.setInitialSize(10);
        dataSource.setMinIdle(10);
        dataSource.setMaxWait(60 * 1000);
        dataSource.setMaxActive(20);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(10);
        return dataSource;
    }

    // 兼容性方法 - 使用默认数据源配置
    protected DataSource getClickHouseDataSource() {
        return getClickHouseDataSource(getDefaultDynamicDataSource());
    }

    protected DataSource getMySqlDataSource() {
        return getMySqlDataSource(getDefaultDynamicDataSource());
    }

    protected DataSource getPostgreSqlDataSource() {
        return getPostgreSqlDataSource(getDefaultDynamicDataSource());
    }

    protected DataSource getSqliteDataSource() {
        DynamicDataSource config = new DynamicDataSource();
        config.setDbName("D:/my/sqlite/information.db");
        return getSqliteDataSource(config);
    }

    protected DataSource getDmDataSource() {
        return getDmDataSource(getDefaultDynamicDataSource());
    }

    protected DataSource getSqlServerDataSource() {
        return getSqlServerDataSource(getDefaultDynamicDataSource());
    }

    protected DataSource getOracleDataSource() {
        return getOracleDataSource(getDefaultDynamicDataSource());
    }

    protected DataSource getDb2DataSource() {
        return getDb2DataSource(getDefaultDynamicDataSource());
    }

    /**
     * 根据配置创建数据源（内部方法）
     */
    private DataSource createDataSourceFromConfig(DynamicDataSource config) {
        return getDataSourceByConfig(config);
    }

    /**
     * 测试数据源连接
     */
    public boolean testConnection(DynamicDataSource dataSourceConfig) {
        try {
            DataSource dataSource = getDataSourceByConfig(dataSourceConfig);
            try (Connection connection = dataSource.getConnection()) {
                return connection.isValid(5);
            }
        } catch (Exception e) {
            logger.error("测试数据源连接失败: {}", e.getMessage());
            return false;
        }
    }

    /**
     * 获取所有可用的数据源配置
     */
    public List<DynamicDataSource> getAllDataSources() {
        try {
            IDynamicDataSourceService service = getDynamicDataSourceService();
            if (service != null) {
                return service.selectDynamicDataSourceList(new DynamicDataSource());
            }
        } catch (Exception e) {
            logger.warn("获取所有数据源配置失败: {}", e.getMessage());
        }
        return new java.util.ArrayList<>();
    }

    /**
     * 检查数据源是否存在
     */
    public boolean dataSourceExists(Long id) {
        try {
            IDynamicDataSourceService service = getDynamicDataSourceService();
            if (service != null) {
                return service.selectDynamicDataSourceById(id) != null;
            }
        } catch (Exception e) {
            logger.warn("检查数据源是否存在失败: {}", e.getMessage());
        }
        return false;
    }
}
