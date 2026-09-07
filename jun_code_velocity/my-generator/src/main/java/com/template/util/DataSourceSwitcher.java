package com.template.util;

import com.template.config.DatabaseSourceConfig;
import com.template.domain.DynamicDataSource;
import com.template.domain.GenTable;
import com.template.domain.GenTableColumn;
import com.template.service.IDynamicDataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据源切换工具
 *
 * @author Wujun
 */
@Component
public class DataSourceSwitcher {

    private static final Logger log = LoggerFactory.getLogger(DataSourceSwitcher.class);

    @Autowired
    private IDynamicDataSourceService dynamicDataSourceService;

    @Autowired
    private DatabaseSourceConfig databaseSourceConfig;

    public List<GenTable> selectDbTableList(Long dataSourceId, String tableName) {
        List<GenTable> result = new ArrayList<>();
        DynamicDataSource config = dynamicDataSourceService.selectDynamicDataSourceById(dataSourceId);
        if (config == null) return result;

        DataSource ds = databaseSourceConfig.getDataSourceByConfig(config);
        String sql = "SELECT table_name, table_comment, create_time, update_time FROM information_schema.tables WHERE table_schema = ?";
        if (tableName != null && !tableName.isEmpty()) {
            sql += " AND lower(table_name) LIKE lower(?)";
        }
        sql += " AND table_name NOT LIKE 'qrtz_%' AND table_name NOT LIKE 'gen_%' ORDER BY create_time DESC";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, config.getDbName());
            if (tableName != null && !tableName.isEmpty()) {
                ps.setString(2, "%" + tableName + "%");
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GenTable table = new GenTable();
                    table.setTableName(rs.getString("table_name"));
                    table.setTableComment(rs.getString("table_comment"));
                    table.setCreateTime(rs.getDate("create_time"));
                    result.add(table);
                }
            }
        } catch (SQLException e) {
            log.error("查询动态数据源表列表失败", e);
        }
        return result;
    }

    public List<GenTableColumn> selectDbTableColumnsByName(Long dataSourceId, String tableName) {
        List<GenTableColumn> result = new ArrayList<>();
        DynamicDataSource config = dynamicDataSourceService.selectDynamicDataSourceById(dataSourceId);
        if (config == null) return result;

        DataSource ds = databaseSourceConfig.getDataSourceByConfig(config);
        String sql = "SELECT column_name, (CASE WHEN (is_nullable = 'no' && column_key != 'PRI') THEN '1' ELSE NULL END) AS is_required, " +
                "(CASE WHEN column_key = 'PRI' THEN '1' ELSE '0' END) AS is_pk, ordinal_position AS sort, column_comment, " +
                "(CASE WHEN extra = 'auto_increment' THEN '1' ELSE '0' END) AS is_increment, column_type " +
                "FROM information_schema.columns WHERE table_schema = ? AND table_name = ? ORDER BY ordinal_position";

        try (Connection conn = ds.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, config.getDbName());
            ps.setString(2, tableName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    GenTableColumn column = new GenTableColumn();
                    column.setColumnName(rs.getString("column_name"));
                    column.setColumnComment(rs.getString("column_comment"));
                    column.setColumnType(rs.getString("column_type"));
                    column.setIsPk(rs.getString("is_pk"));
                    column.setIsRequired(rs.getString("is_required"));
                    column.setSort(rs.getInt("sort"));
                    column.setIsIncrement(rs.getString("is_increment"));
                    result.add(column);
                }
            }
        } catch (SQLException e) {
            log.error("查询动态数据源表字段失败", e);
        }
        return result;
    }
}