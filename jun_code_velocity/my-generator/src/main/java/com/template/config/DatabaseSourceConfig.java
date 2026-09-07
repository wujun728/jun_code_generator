package com.template.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.template.domain.DynamicDataSource;
import com.template.service.IDynamicDataSourceService;
import com.template.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * 多数据源配置
 *
 * @author Wujun
 */
@Configuration
public class DatabaseSourceConfig {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseSourceConfig.class);

    private static volatile Long currentDataSourceId;

    @Value("${spring.datasource.url:jdbc:mysql://localhost:3306/db_generator?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true&autoReconnect=true}")
    private String defaultUrl;

    @Value("${spring.datasource.username:root}")
    private String defaultUsername;

    @Value("${spring.datasource.password:123456}")
    private String defaultPassword;

    @Value("${spring.datasource.driver-class-name:com.mysql.cj.jdbc.Driver}")
    private String defaultDriverClassName;

    public static Long getCurrentDataSourceId() {
        return currentDataSourceId;
    }

    public static void setCurrentDataSourceId(Long id) {
        currentDataSourceId = id;
    }

    public static void clearCurrentDataSourceId() {
        currentDataSourceId = null;
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        return createDefaultDataSource();
    }

    public DruidDataSource createDefaultDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(defaultUrl);
        dataSource.setUsername(defaultUsername);
        dataSource.setPassword(defaultPassword);
        dataSource.setDriverClassName(defaultDriverClassName);
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

    public DataSource getDataSourceByConfig(DynamicDataSource config) {
        if (config == null) return null;
        String type = config.getType();
        switch (type) {
            case "0": return getMySqlDataSource(config);
            case "1": return getOracleDataSource(config);
            case "2": return getSqlServerDataSource(config);
            case "3": return getPostgreSqlDataSource(config);
            case "4": return getClickHouseDataSource(config);
            case "5": return getSqliteDataSource(config);
            case "6": return getDb2DataSource(config);
            case "7": return getDmDataSource(config);
            default: return getMySqlDataSource(config);
        }
    }

    public boolean testConnection(DynamicDataSource config) {
        try {
            DataSource ds = getDataSourceByConfig(config);
            try (Connection conn = ds.getConnection()) {
                return conn.isValid(5);
            }
        } catch (Exception e) {
            logger.error("测试数据源连接失败: {}", e.getMessage());
            return false;
        }
    }

    public List<DynamicDataSource> getAllDataSources() {
        return new ArrayList<>();
    }

    private DruidDataSource createDruidDataSource(String url, String driverClassName, String username, String password) {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl(url);
        ds.setDriverClassName(driverClassName);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setInitialSize(5);
        ds.setMinIdle(5);
        ds.setMaxWait(60 * 1000);
        ds.setMaxActive(20);
        ds.setTestWhileIdle(true);
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setMaxPoolPreparedStatementPerConnectionSize(10);
        return ds;
    }

    public DataSource getMySqlDataSource(DynamicDataSource config) {
        String url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&zeroDateTimeBehavior=convertToNull&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true",
                config.getUrl(), config.getPort(), config.getDbName());
        return createDruidDataSource(url, "com.mysql.cj.jdbc.Driver", config.getUsername(), config.getPassword());
    }

    public DataSource getOracleDataSource(DynamicDataSource config) {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:oracle:thin:@//" + config.getUrl() + ":" + config.getPort() + "/" + config.getDbName());
        ds.setDriverClassName("oracle.jdbc.OracleDriver");
        ds.setUsername(config.getUsername());
        ds.setPassword(config.getPassword());
        ds.setInitialSize(10); ds.setMinIdle(10); ds.setMaxWait(60 * 1000); ds.setMaxActive(20);
        return ds;
    }

    public DataSource getSqlServerDataSource(DynamicDataSource config) {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:sqlserver://" + config.getUrl() + ":" + config.getPort() + ";databaseName=" + config.getDbName() + ";encrypt=false");
        ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        ds.setUsername(config.getUsername()); ds.setPassword(config.getPassword());
        ds.setInitialSize(10); ds.setMinIdle(10); ds.setMaxWait(60 * 1000); ds.setMaxActive(20);
        return ds;
    }

    public DataSource getPostgreSqlDataSource(DynamicDataSource config) {
        String url = String.format("jdbc:postgresql://%s:%s/%s?currentSchema=public", config.getUrl(), config.getPort(), config.getDbName());
        return createDruidDataSource(url, "org.postgresql.Driver", config.getUsername(), config.getPassword());
    }

    public DataSource getClickHouseDataSource(DynamicDataSource config) {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:clickhouse://" + config.getUrl() + ":" + config.getPort() + "/" + config.getDbName());
        ds.setDriverClassName("com.clickhouse.jdbc.ClickHouseDriver");
        ds.setUsername(config.getUsername()); ds.setPassword(config.getPassword());
        ds.setInitialSize(10); ds.setMinIdle(10); ds.setMaxWait(60 * 1000); ds.setMaxActive(20);
        return ds;
    }

    public DataSource getSqliteDataSource(DynamicDataSource config) {
        String url = "jdbc:sqlite:" + config.getDbName();
        return createDruidDataSource(url, "org.sqlite.JDBC", config.getUsername() != null ? config.getUsername() : "", config.getPassword() != null ? config.getPassword() : "");
    }

    public DataSource getDb2DataSource(DynamicDataSource config) {
        DruidDataSource ds = new DruidDataSource();
        ds.setUrl("jdbc:db2://" + config.getUrl() + ":" + config.getPort() + "/" + config.getDbName() + ":currentSchema=ADMINISTRATOR;");
        ds.setDriverClassName("com.ibm.db2.jcc.DB2Driver");
        ds.setUsername(config.getUsername()); ds.setPassword(config.getPassword());
        ds.setInitialSize(10); ds.setMinIdle(10); ds.setMaxWait(60 * 1000); ds.setMaxActive(20);
        return ds;
    }

    public DataSource getDmDataSource(DynamicDataSource config) {
        String url = String.format("jdbc:dm://%s:%s/%s", config.getUrl(), config.getPort(), config.getDbName());
        return createDruidDataSource(url, "dm.jdbc.driver.DmDriver", config.getUsername(), config.getPassword());
    }
}