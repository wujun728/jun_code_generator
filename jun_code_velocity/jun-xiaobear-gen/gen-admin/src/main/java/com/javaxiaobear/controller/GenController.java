package com.javaxiaobear.controller;


import com.alibaba.druid.DbType;
import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlCreateTableStatement;
import com.alibaba.fastjson.JSON;
import com.javaxiaobear.common.AjaxResult;
import com.javaxiaobear.common.BaseController;
import com.javaxiaobear.common.page.TableDataInfo;
import com.javaxiaobear.config.DatabaseSourceConfig;
import com.javaxiaobear.domain.GenTable;
import com.javaxiaobear.domain.GenTableColumn;
import com.javaxiaobear.service.IGenTableColumnService;
import com.javaxiaobear.service.IGenTableService;
import com.javaxiaobear.service.impl.GenTableServiceImpl;
import com.javaxiaobear.text.Convert;
import com.javaxiaobear.util.AuditLogUtil;
import com.javaxiaobear.util.StringUtils;
import com.javaxiaobear.generator.CompleteProjectGenerator;
import org.apache.commons.io.IOUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码生成 操作处理
 * 
 * @author javaxiaobear
 */
@RestController
@RequestMapping("/tool/gen")
public class GenController extends BaseController
{
    @Resource
    private IGenTableService genTableService;

    @Resource
    private IGenTableColumnService genTableColumnService;

    @Resource
    private CompleteProjectGenerator completeProjectGenerator;

    /**
     * 定义常用的 sql关键字
     */
    public static String SQL_REGEX = "\u000B|and |extractvalue|updatexml|sleep|exec |insert |select |delete |update |drop |count |chr |mid |master |truncate |char |declare |or |union |like |+|/*|user()";


    /**
     * 查询代码生成列表
     */
    @GetMapping("/list")
    public TableDataInfo genList(GenTable genTable)
    {
        startPage();
        List<GenTable> list = genTableService.selectGenTableList(genTable);
        return getDataTable(list);
    }

    /**
     * 修改代码生成业务
     */
    @GetMapping(value = "/{talbleId}")
    public AjaxResult getInfo(@PathVariable Long talbleId)
    {
        GenTable table = genTableService.selectGenTableById(talbleId);
        List<GenTable> tables = genTableService.selectGenTableAll();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(talbleId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("info", table);
        map.put("rows", list);
        map.put("tables", tables);
        return AjaxResult.success(map);
    }

    /**
     * 查询数据库列表
     */
    @GetMapping("/db/list")
    public TableDataInfo dataList(GenTable genTable, @RequestParam(value = "dataSourceId", required = false) Long dataSourceId)
    {
        // 如果指定了数据源ID，切换到对应的数据源
        if (dataSourceId != null) {
            com.javaxiaobear.config.DatabaseSourceConfig.setCurrentDataSourceId(dataSourceId);
        }

        try {
            startPage();
            List<GenTable> list = genTableService.selectDbTableList(genTable);
            return getDataTable(list);
        } finally {
            // 清除数据源设置
            if (dataSourceId != null) {
//                com.javaxiaobear.config.DatabaseSourceConfig.clearCurrentDataSourceId();
            }
        }
    }

    /**
     * 查询数据表字段列表
     */
    @GetMapping(value = "/column/{talbleId}")
    public TableDataInfo columnList(Long tableId)
    {
        TableDataInfo dataInfo = new TableDataInfo();
        List<GenTableColumn> list = genTableColumnService.selectGenTableColumnListByTableId(tableId);
        dataInfo.setRows(list);
        dataInfo.setTotal(list.size());
        return dataInfo;
    }

    /**
     * 导入表结构（保存）
     */
    @PostMapping("/importTable")
    public AjaxResult importTableSave(@RequestBody String tables, HttpServletRequest request)
    {
        HashMap hashMap = JSON.parseObject(tables, HashMap.class);
        tables = (String) hashMap.get("tableNames");
        String[] tableNames = Convert.toStrArray(tables);

        logger.info("开始导入表结构，表数量: {}, 表名: {}", tableNames.length, String.join(",", tableNames));
        AuditLogUtil.PerformanceTimer timer = new AuditLogUtil.PerformanceTimer("IMPORT_TABLES", request);

        try {
            // 查询表信息
            List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
            genTableService.importGenTable(tableList);

            AuditLogUtil.logDatabaseOperation("IMPORT", String.join(",", tableNames),
                    "导入表数量: " + tableNames.length, request);
            timer.end("表结构导入成功，表数量: " + tableNames.length);
            logger.info("表结构导入完成，表数量: {}", tableNames.length);

            return AjaxResult.success();
        } catch (Exception e) {
            AuditLogUtil.logDatabaseOperation("IMPORT", String.join(",", tableNames),
                    "导入失败: " + e.getMessage(), request);
            timer.end("表结构导入失败");
            logger.error("表结构导入失败，表数量: {}", tableNames.length, e);
            throw e;
        }
    }

    /**
     * 创建表
     * @param sql
     * @return
     */
    @PostMapping("/create")
    public AjaxResult createNewTable(@RequestBody String sql){
        HashMap hashMap = JSON.parseObject(sql, HashMap.class);
        sql = (String) hashMap.get("sql");
        try
        {
            filterKeyword(sql);
            List<SQLStatement> sqlStatements = SQLUtils.parseStatements(sql, DbType.mysql);
            List<String> tableNames = new ArrayList<>();
            for (SQLStatement sqlStatement : sqlStatements)
            {
                if (sqlStatement instanceof MySqlCreateTableStatement)
                {
                    MySqlCreateTableStatement createTableStatement = (MySqlCreateTableStatement) sqlStatement;
                    if (genTableService.createNewTable(createTableStatement.toString()))
                    {
                        String tableName = createTableStatement.getTableName().replaceAll("`", "");
                        tableNames.add(tableName);
                    }
                }
            }
            List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames.toArray(new String[tableNames.size()]));
            genTableService.importGenTable(tableList);
            return AjaxResult.success();
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("创建表结构异常");
        }
    }
    /**
     * SQL关键字检查
     */
    public static void filterKeyword(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            return;
        }
        String[] sqlKeywords = StringUtils.split(SQL_REGEX, "\\|");
        for (String sqlKeyword : sqlKeywords)
        {
            if (StringUtils.indexOfIgnoreCase(value, sqlKeyword) > -1)
            {
                throw new RuntimeException("参数存在SQL注入风险");
            }
        }
    }


    /**
     * 导出至excel
     * @param response
     * @param tableIds
     * @return
     */
    @GetMapping("/export")
    public void export(HttpServletResponse response, Long[] tableIds) throws IOException {
        genTableService.exportToExcel(response, tableIds);
    }

    /**
     * 修改保存代码生成业务
     */
    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody GenTable genTable)
    {
        genTableService.validateEdit(genTable);
        genTableService.insertGenTable(genTable);
        return AjaxResult.success();
    }

    /**
     * 修改保存代码生成业务
     */
    @PostMapping
    public AjaxResult editSave(@Validated @RequestBody GenTable genTable)
    {
        genTableService.validateEdit(genTable);
        genTableService.updateGenTable(genTable);
        return AjaxResult.success();
    }

    /**
     * 删除代码生成
     */
    @DeleteMapping("/delete/{tableIds}")
    public AjaxResult remove(@PathVariable Long[] tableIds)
    {
        genTableService.deleteGenTableByIds(tableIds);
        return AjaxResult.success();
    }

    /**
     * 预览代码
     */
    @GetMapping("/preview/{tableId}")
    public AjaxResult preview(@PathVariable("tableId") Long tableId) throws IOException
    {
        Map<String, String> dataMap = genTableService.previewCode(tableId);
        return AjaxResult.success(dataMap);
    }

    /**
     * 生成代码（下载方式）
     */
    @GetMapping("/download/{tableName}")
    public void download(HttpServletResponse response, @PathVariable("tableName") String tableName, HttpServletRequest request) throws IOException
    {
        logger.info("开始生成代码下载，表名: {}", tableName);
        AuditLogUtil.PerformanceTimer timer = new AuditLogUtil.PerformanceTimer("CODE_DOWNLOAD", request);

        try {
            byte[] data = genTableService.downloadCode(tableName);
            genCode(response, data);

            AuditLogUtil.logCodeGeneration(tableName, "DOWNLOAD", "SUCCESS", request);
            timer.end("代码生成下载成功");
            logger.info("代码生成下载完成，表名: {}", tableName);
        } catch (Exception e) {
            AuditLogUtil.logCodeGeneration(tableName, "DOWNLOAD", "FAILED: " + e.getMessage(), request);
            timer.end("代码生成下载失败");
            logger.error("代码生成下载失败，表名: {}", tableName, e);
            throw e;
        }
    }

    /**
     * 生成代码（自定义路径）
     */
    @GetMapping("/genCode/{tableName}")
    public AjaxResult genCode(@PathVariable("tableName") String tableName)
    {
        genTableService.generatorCode(tableName);
        return AjaxResult.success();
    }

    /**
     * 同步数据库
     */
    @GetMapping("/synchDb/{tableName}")
    public AjaxResult synchDb(@PathVariable("tableName") String tableName)
    {
        genTableService.synchDb(tableName);
        return AjaxResult.success();
    }

    /**
     * 批量生成代码
     */
    @GetMapping("/batchGenCode")
    public void batchGenCode(HttpServletResponse response, String tables, HttpServletRequest request) throws IOException
    {
        String[] tableNames = Convert.toStrArray(tables);
        logger.info("开始批量生成代码，表数量: {}, 表名: {}", tableNames.length, String.join(",", tableNames));
        AuditLogUtil.PerformanceTimer timer = new AuditLogUtil.PerformanceTimer("BATCH_CODE_GENERATION", request);

        try {
            byte[] data = genTableService.downloadCode(tableNames);
            genCode(response, data);

            AuditLogUtil.logCodeGeneration(String.join(",", tableNames), "BATCH_DOWNLOAD", "SUCCESS", request);
            timer.end("批量代码生成成功，表数量: " + tableNames.length);
            logger.info("批量代码生成完成，表数量: {}", tableNames.length);
        } catch (Exception e) {
            AuditLogUtil.logCodeGeneration(String.join(",", tableNames), "BATCH_DOWNLOAD", "FAILED: " + e.getMessage(), request);
            timer.end("批量代码生成失败");
            logger.error("批量代码生成失败，表数量: {}", tableNames.length, e);
            throw e;
        }
    }

    /**
     * 生成完整项目（一键生成可用代码）
     */
    @PostMapping("/generateCompleteProject")
    public void generateCompleteProject(HttpServletResponse response, @RequestBody Map<String, Object> params, HttpServletRequest request) throws IOException
    {
        logger.info("开始生成完整项目");
        AuditLogUtil.PerformanceTimer timer = new AuditLogUtil.PerformanceTimer("COMPLETE_PROJECT_GENERATION", request);

        try {
            // 解析参数
            List<String> tableNamesList = (List<String>) params.get("tableNames");
            String[] tableNames = tableNamesList.toArray(new String[0]);
            Map<String, Object> projectConfig = (Map<String, Object>) params.get("projectConfig");

            // 获取表信息
            List<GenTable> tables = new ArrayList<>();
            for (String tableName : tableNames) {
                // 使用现有的查询方法
                GenTable queryTable = new GenTable();
                queryTable.setTableName(tableName);
                List<GenTable> tableList = genTableService.selectGenTableList(queryTable);
                if (!tableList.isEmpty()) {
                    GenTable table = tableList.get(0);
                    // 获取完整的表信息，包括列信息
                    GenTable fullTable = genTableService.selectGenTableById(table.getTableId());
                    if (fullTable != null) {
                        // 设置主子表信息
                        ((GenTableServiceImpl) genTableService).setSubTable(fullTable);
                        // 设置主键列信息
                        ((GenTableServiceImpl) genTableService).setPkColumn(fullTable);
                        tables.add(fullTable);
                    }
                }
            }

            // 创建项目配置
            CompleteProjectGenerator.ProjectConfig config = createProjectConfig(projectConfig);

            // 生成完整项目
            byte[] data = completeProjectGenerator.generateCompleteProject(tables, config);

            // 使用统一的genCode方法返回压缩包
            genCodeWithFilename(response, data, config.getProjectName() + "-complete.zip");

            AuditLogUtil.logCodeGeneration(String.join(",", tableNames), "COMPLETE_PROJECT", "SUCCESS", request);
            timer.end("完整项目生成成功，表数量: " + tableNames.length);
            logger.info("完整项目生成完成，项目名: {}, 表数量: {}", config.getProjectName(), tableNames.length);

        } catch (Exception e) {
            AuditLogUtil.logCodeGeneration("COMPLETE_PROJECT", "GENERATION", "FAILED: " + e.getMessage(), request);
            timer.end("完整项目生成失败");
            logger.error("完整项目生成失败", e);
            throw e;
        }
    }

    /**
     * 创建项目配置
     */
    private CompleteProjectGenerator.ProjectConfig createProjectConfig(Map<String, Object> configMap) {
        CompleteProjectGenerator.ProjectConfig config = new CompleteProjectGenerator.ProjectConfig();

        config.setProjectName((String) configMap.getOrDefault("projectName", "demo-project"));
        config.setProjectComment((String) configMap.getOrDefault("projectComment", "演示项目"));
        config.setPackageName((String) configMap.getOrDefault("packageName", "com.example.demo"));
        config.setClassName((String) configMap.getOrDefault("className", "Demo"));
        config.setGroupId((String) configMap.getOrDefault("groupId", "com.example"));
        config.setArtifactId((String) configMap.getOrDefault("artifactId", "demo"));
        config.setVersion((String) configMap.getOrDefault("version", "1.0.0"));
        config.setAuthor((String) configMap.getOrDefault("author", "javaxiaobear"));

        // 数据库配置
        config.setDriverClassName((String) configMap.getOrDefault("driverClassName", "com.mysql.cj.jdbc.Driver"));
        config.setDatasourceUrl((String) configMap.getOrDefault("datasourceUrl", "jdbc:mysql://localhost:3306/demo"));
        config.setDatasourceUsername((String) configMap.getOrDefault("datasourceUsername", "root"));
        config.setDatasourcePassword((String) configMap.getOrDefault("datasourcePassword", "123456"));

        // 服务配置
        config.setServerPort((Integer) configMap.getOrDefault("serverPort", 8080));
        config.setJwtSecret((String) configMap.getOrDefault("jwtSecret", "javaxiaobear-secret-key"));

        return config;
    }

    /**
     * 使用指定数据源查询数据库表列表
     */
    @GetMapping("/db/list/{dataSourceId}")
    public TableDataInfo dataListWithDataSource(@PathVariable("dataSourceId") Long dataSourceId, GenTable genTable)
    {
        // 切换到指定的数据源
        DatabaseSourceConfig.setCurrentDataSourceId(dataSourceId);

        try {
            startPage();
            List<GenTable> list = genTableService.selectDbTableList(genTable);
            return getDataTable(list);
        } finally {
            // 清除数据源设置
//            DatabaseSourceConfig.clearCurrentDataSourceId();
        }
    }

    /**
     * 使用指定数据源导入表结构
     */
    @PostMapping("/importTable/{dataSourceId}")
    public AjaxResult importTableWithDataSource(@PathVariable("dataSourceId") Long dataSourceId, @RequestBody String tables)
    {
        // 切换到指定的数据源
        DatabaseSourceConfig.setCurrentDataSourceId(dataSourceId);

        try {
            HashMap hashMap = JSON.parseObject(tables, HashMap.class);
            tables = (String) hashMap.get("tableNames");
            String[] tableNames = Convert.toStrArray(tables);
            // 查询表信息
            List<GenTable> tableList = genTableService.selectDbTableListByNames(tableNames);
            genTableService.importGenTable(tableList);
            return AjaxResult.success();
        } finally {
            // 清除数据源设置
//            DatabaseSourceConfig.clearCurrentDataSourceId();
        }
    }

    /**
     * 生成zip文件
     */
    private void genCode(HttpServletResponse response, byte[] data) throws IOException
    {
        genCodeWithFilename(response, data, "xiaobear.zip");
    }

    /**
     * 生成zip文件（自定义文件名）
     */
    private void genCodeWithFilename(HttpServletResponse response, byte[] data, String filename) throws IOException
    {
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Expose-Headers", "Content-Disposition");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");
        IOUtils.write(data, response.getOutputStream());
    }
}