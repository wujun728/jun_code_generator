package com.jeasy.db;

import com.jeasy.common.Func;
import com.jeasy.common.str.StrKit;
import com.jeasy.conf.Config;
import com.jeasy.util.CommentKit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author taomk
 * @version 1.0
 * @since 2014/10/19 11:23
 */
@Slf4j
@Data
public final class DbInfo {

    private DbInfo() {
    }

    private String productName;

    private String productVersion;

    private String catalogSeparator;

    private String driverVersion;

    private List<TableInfo> tables;

    public static DbInfo getInstance(Config conf) {

        DbInfo info = new DbInfo();

        try {
            Class.forName(conf.getDriverClass());
            Connection con = DriverManager.getConnection(conf.getJdbcUrl(), conf.getUserName(), conf.getUserPwd());

            DatabaseMetaData dbmd = con.getMetaData();
            info.setProductName(dbmd.getDatabaseProductName());
            info.setProductVersion(dbmd.getDatabaseProductVersion());
            info.setCatalogSeparator(dbmd.getCatalogSeparator());
            info.setDriverVersion(dbmd.getDriverVersion());

            List<TableInfo> tables = new ArrayList<>();
            info.setTables(tables);

            ResultSet rs = dbmd.getTables(conf.getDbName(), null, null, new String[]{"TABLE"});
            while (rs.next()) {

                String tableName = rs.getString("TABLE_NAME");
                boolean isContinue = (Func.isNotEmpty(conf.getTableSet()) && !conf.getTableSet().contains(tableName)) ||
                    (Func.isEmpty(conf.getTableSet()) && !StrKit.equalsIgnoreCase("*", conf.getTables()) && !tableName.substring(0, 2).equals(conf.getTables().substring(0, 2)));
                if (isContinue) {
                    continue;
                }

                String comment = getCommentByTableName(tableName, con);
                List<ColumnInfo> columns = new ArrayList<>();

                TableInfo tableInfo = new TableInfo(tableName, comment);
                tableInfo.setColumns(columns);

                ResultSet columnRs = dbmd.getColumns(conf.getDbName(), null, tableName, null);
                while (columnRs.next()) {
                    columns.add(new ColumnInfo(columnRs.getString("COLUMN_NAME"), columnRs.getString("TYPE_NAME"), columnRs.getString("REMARKS")));
                }

                tables.add(tableInfo);
                log.info("read table : " + tableInfo.getName() + " from database success");
            }
        } catch (Exception e) {
            log.error("error exception : ", e);
        }

        return info;
    }

    /**
     * 获得某表的建表语句
     *
     * @param tableName
     * @return
     * @throws Exception
     */
    private static String getCommentByTableName(String tableName, Connection con) throws Exception {
        try (
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + tableName)) {
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);
                rs.close();
                stmt.close();
                return CommentKit.parse(createDDL);
            }
        }
        return StrKit.S_EMPTY;
    }
}
