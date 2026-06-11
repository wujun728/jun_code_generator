package com.javaxiaobear.util;

import com.javaxiaobear.config.DatabaseSourceConfig;
import com.javaxiaobear.domain.DynamicDataSource;
import com.javaxiaobear.domain.GenTable;
import com.javaxiaobear.domain.GenTableColumn;
import com.javaxiaobear.service.IDynamicDataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源切换工具类
 * 用于在需要时直接连接到指定的数据源执行查询
 * 
 * @author javaxiaobear
 * @date 2025-01-08
 */
@Component
public class DataSourceSwitcher {
    
    private static final Logger logger = LoggerFactory.getLogger(DataSourceSwitcher.class);
    
    @Autowired
    private IDynamicDataSourceService dynamicDataSourceService;
    
    @Autowired
    private DatabaseSourceConfig databaseSourceConfig;
    
    /**
     * 使用指定数据源执行查询
     */
    public <T> T executeWithDataSource(Long dataSourceId, DataSourceCallback<T> callback) {
        if (dataSourceId == null) {
            // 使用默认数据源
            return callback.execute(null, "default_db");
        }
        
        DynamicDataSource config = dynamicDataSourceService.selectDynamicDataSourceById(dataSourceId);
        if (config == null) {
            logger.warn("数据源配置不存在: {}", dataSourceId);
            return callback.execute(null, "default_db");
        }
        
        DataSource dataSource = null;
        Connection connection = null;
        try {
            dataSource = databaseSourceConfig.getDataSourceByConfig(config);
            connection = dataSource.getConnection();
            return callback.execute(connection, config.getDbName());
        } catch (Exception e) {
            logger.error("执行数据源切换查询失败: {}", dataSourceId, e);
            return null;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("关闭连接失败", e);
                }
            }
        }
    }
    
    /**
     * 查询数据库表列表
     */
    public List<GenTable> selectDbTableList(Long dataSourceId, String tableName) {
        return executeWithDataSource(dataSourceId, (connection, dbName) -> {
            if (connection == null) {
                return new ArrayList<>();
            }
            
            List<GenTable> tables = new ArrayList<>();
            String sql = buildTableListSql(dbName, tableName);
            
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                if (StringUtils.isNotEmpty(tableName)) {
                    ps.setString(1, "%" + tableName + "%");
                }
                
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        GenTable table = new GenTable();
                        table.setTableName(rs.getString("table_name"));
                        table.setTableComment(rs.getString("table_comment"));
                        table.setCreateTime(rs.getTimestamp("create_time"));
                        table.setUpdateTime(rs.getTimestamp("update_time"));
                        tables.add(table);
                    }
                }
            } catch (SQLException e) {
                logger.error("查询数据库表列表失败", e);
            }
            
            return tables;
        });
    }
    
    /**
     * 查询表字段列表
     */
    public List<GenTableColumn> selectDbTableColumnsByName(Long dataSourceId, String tableName) {
        return executeWithDataSource(dataSourceId, (connection, dbName) -> {
            if (connection == null) {
                return new ArrayList<>();
            }
            
            List<GenTableColumn> columns = new ArrayList<>();
            String sql = buildColumnListSql(dbName, tableName);
            
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        GenTableColumn column = new GenTableColumn();
                        column.setColumnName(rs.getString("column_name"));
                        column.setIsRequired(rs.getString("is_required"));
                        column.setSort(rs.getInt("sort"));
                        column.setIsPk(rs.getString("is_pk"));
                        column.setColumnComment(rs.getString("column_comment"));
                        column.setIsIncrement(rs.getString("is_increment"));
                        column.setColumnType(rs.getString("column_type"));
                        columns.add(column);
                    }
                }
            } catch (SQLException e) {
                logger.error("查询表字段列表失败", e);
            }
            
            return columns;
        });
    }
    
    /**
     * 构建查询表列表的SQL
     */
    private String buildTableListSql(String dbName, String tableName) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT table_name, table_comment, create_time, update_time ");
        sql.append("FROM information_schema.tables ");
        sql.append("WHERE table_schema = '").append(dbName).append("' ");
        sql.append("AND table_type = 'BASE TABLE' ");
        
        if (StringUtils.isNotEmpty(tableName)) {
            sql.append("AND table_name LIKE ? ");
        }
        
        sql.append("ORDER BY table_name");
        return sql.toString();
    }
    
    /**
     * 构建查询字段列表的SQL
     */
    private String buildColumnListSql(String dbName, String tableName) {
        return "select column_name, " +
                "(case when (is_nullable = 'NO' and column_key != 'PRI') then '1' else null end) as is_required, " +
                "(case when column_key = 'PRI' then '1' else '0' end) as is_pk, " +
                "ordinal_position as sort, column_comment, " +
                "(case when extra = 'auto_increment' then '1' else '0' end) as is_increment, " +
                "column_type " +
                "FROM information_schema.columns " +
                "WHERE table_name = '" + tableName +"' " +
                "AND table_schema = '" + dbName + "' " +
                "ORDER BY ordinal_position";
    }
    
    /**
     * 数据源回调接口
     */
    @FunctionalInterface
    public interface DataSourceCallback<T> {
        T execute(Connection connection, String dbName);
    }
}
