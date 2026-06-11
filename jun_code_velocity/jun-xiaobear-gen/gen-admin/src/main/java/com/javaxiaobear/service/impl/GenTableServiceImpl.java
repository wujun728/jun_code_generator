package com.javaxiaobear.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.javaxiaobear.config.DatabaseSourceConfig;
import com.javaxiaobear.config.GenConfig;
import com.javaxiaobear.constants.GenConstants;
import com.javaxiaobear.domain.DynamicDataSource;
import com.javaxiaobear.domain.GenTable;
import com.javaxiaobear.domain.GenTableColumn;
import com.javaxiaobear.mapper.GenTableColumnMapper;
import com.javaxiaobear.mapper.GenTableMapper;
import com.javaxiaobear.service.IDynamicDataSourceService;
import com.javaxiaobear.service.IGenTableService;
import com.javaxiaobear.text.CharsetKit;
import com.javaxiaobear.util.*;
import com.javaxiaobear.util.DataSourceSwitcher;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 业务 服务层实现
 * 
 * @author javaxiaobear
 */
@Service
public class GenTableServiceImpl implements IGenTableService
{
    private static final Logger log = LoggerFactory.getLogger(GenTableServiceImpl.class);

    private String operName = GenConfig.getAuthor();

    @Resource
    private IDynamicDataSourceService dynamicDataSourceService;

    @Resource
    private DataSourceSwitcher dataSourceSwitcher;

    /**
     * UTF-8 字符集
     */
    private static final String UTF8 = "UTF-8";

    @Resource
    private GenTableMapper genTableMapper;

    @Resource
    private GenTableColumnMapper genTableColumnMapper;

    /**
     * 获取当前数据源的数据库名称
     */
    private String getCurrentDataBase() {
        Long currentDataSourceId = DatabaseSourceConfig.getCurrentDataSourceId();
        if (currentDataSourceId != null) {
            // 使用指定的数据源
            DynamicDataSource dataSourceConfig = dynamicDataSourceService.selectDynamicDataSourceById(currentDataSourceId);
            if (dataSourceConfig != null) {
                log.info("使用指定的数据源: {}", dataSourceConfig.toString());
                return dataSourceConfig.getDbName();
            }
        }
        // 使用默认数据源的数据库名称
        return GenConfig.getDataBase();
    }

    /**
     * 查询业务信息
     *
     * @param id 业务ID
     * @return 业务信息
     */
    @Override
    public GenTable selectGenTableById(Long id)
    {
        GenTable genTable = genTableMapper.selectGenTableById(id);
        setTableFromOptions(genTable);
        return genTable;
    }

    /**
     * 根据表ID数组查询业务信息
     *
     * @param tableIds 表ID数组
     * @return 业务信息集合
     */
    @Override
    public List<GenTable> selectGenTableByIds(String[] tableIds)
    {
        return genTableMapper.selectGenTableByIds(tableIds);
    }

    /**
     * 查询业务列表
     * 
     * @param genTable 业务信息
     * @return 业务集合
     */
    @Override
    public List<GenTable> selectGenTableList(GenTable genTable)
    {
        if (StringUtils.isNotNull(genTable.getSelectId())){
            DatabaseSourceConfig.setCurrentDataSourceId(genTable.getSelectId());
        }
        return genTableMapper.selectGenTableList(genTable);
    }

    /**
     * 查询据库列表
     *
     * @param genTable 业务信息
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableList(GenTable genTable)
    {
        Long currentDataSourceId = DatabaseSourceConfig.getCurrentDataSourceId();
        if (currentDataSourceId != null) {
            // 使用动态数据源查询
            List<GenTable> result = dataSourceSwitcher.selectDbTableList(currentDataSourceId, genTable.getTableName());

            return result;
        } else {
            // 使用默认数据源查询
            genTable.setDataBase(getCurrentDataBase());
            return genTableMapper.selectDbTableList(genTable);
        }
    }

    /**
     * 查询据库列表
     * 
     * @param tableNames 表名称组
     * @return 数据库表集合
     */
    @Override
    public List<GenTable> selectDbTableListByNames(String[] tableNames)
    {
        Long currentDataSourceId = DatabaseSourceConfig.getCurrentDataSourceId();
        if (currentDataSourceId!= null) {
            // 使用动态数据源查询
            return dataSourceSwitcher.selectDbTableList(currentDataSourceId, Arrays.toString(tableNames));
        }
        return genTableMapper.selectDbTableListByNames(tableNames, getCurrentDataBase());
    }

    /**
     * 查询所有表信息
     * 
     * @return 表信息集合
     */
    @Override
    public List<GenTable> selectGenTableAll()
    {
        return genTableMapper.selectGenTableAll();
    }

    /**
     * 修改业务
     * 
     * @param genTable 业务信息
     * @return 结果
     */
    @Override
    @Transactional
    public void updateGenTable(GenTable genTable)
    {
        String options = JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
        int row = genTableMapper.updateGenTable(genTable);
        if (row > 0)
        {
            for (GenTableColumn cenTableColumn : genTable.getColumns())
            {
                genTableColumnMapper.updateGenTableColumn(cenTableColumn);
            }
        }
    }

    /**
     * 删除业务对象
     * 
     * @param tableIds 需要删除的数据ID
     * @return 结果
     */
    @Override
    @Transactional
    public void deleteGenTableByIds(Long[] tableIds)
    {
        genTableMapper.deleteGenTableByIds(tableIds);
        genTableColumnMapper.deleteGenTableColumnByIds(tableIds);
    }

    /**
     * 导入表结构
     * 
     * @param tableList 导入表列表
     */
    @Override
    @Transactional
    public void importGenTable(List<GenTable> tableList)
    {
        try
        {
            for (GenTable table : tableList)
            {
                String tableName = table.getTableName();
                GenUtils.initTable(table, operName);
                table.setUpdateTime(new Date());
                int row = genTableMapper.insertGenTable(table);
                if (row > 0)
                {
                    // 保存列信息
                    List<GenTableColumn> genTableColumns = getTableColumns(tableName);
                    for (GenTableColumn column : genTableColumns)
                    {
                        GenUtils.initColumnField(column, table);
                        genTableColumnMapper.insertGenTableColumn(column);
                    }
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("导入失败：" + e.getMessage());
        }
    }

    /**
     * 预览代码
     * 
     * @param tableId 表编号
     * @return 预览数据列表
     */
    @Override
    public Map<String, String> previewCode(Long tableId)
    {
        Map<String, String> dataMap = new LinkedHashMap<>();
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableById(tableId);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);
        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getBackEnd(), table.getFrontEnd());
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, UTF8);
            tpl.merge(context, sw);
            dataMap.put(template, sw.toString());
        }
        return dataMap;
    }

    /**
     * 生成代码（下载方式）
     * 
     * @param tableName 表名称
     * @return 数据
     */
    @Override
    public byte[] downloadCode(String tableName)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        generatorCode(tableName, zip);
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 生成代码（自定义路径）
     * 
     * @param tableName 表名称
     */
    @Override
    public void generatorCode(String tableName)
    {
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getBackEnd(), table.getFrontEnd());
        for (String template : templates)
        {
            if (!StringUtils.containsAny(template, "sql.vm", "api.js.vm", "index.vue.vm", "index-tree.vue.vm"))
            {
                // 渲染模板
                StringWriter sw = new StringWriter();
                Template tpl = Velocity.getTemplate(template, UTF8);
                tpl.merge(context, sw);
                try
                {
                    String path = getGenPath(table, template);
                    FileUtils.writeStringToFile(new File(path), sw.toString(), CharsetKit.UTF_8);
                }
                catch (IOException e)
                {
                    throw new RuntimeException("渲染模板失败，表名：" + table.getTableName());
                }
            }
        }
    }

    /**
     * 同步数据库
     * 
     * @param tableName 表名称
     */
    @Override
    @Transactional
    public void synchDb(String tableName)
    {
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        List<GenTableColumn> tableColumns = table.getColumns();
        List<String> tableColumnNames = tableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

        List<GenTableColumn> dbTableColumns = getTableColumns(tableName);
        if (StringUtils.isEmpty(dbTableColumns))
        {
            throw new RuntimeException("同步数据失败，原表结构不存在");
        }
        List<String> dbTableColumnNames = dbTableColumns.stream().map(GenTableColumn::getColumnName).collect(Collectors.toList());

        dbTableColumns.forEach(column -> {
            if (!tableColumnNames.contains(column.getColumnName()))
            {
                GenUtils.initColumnField(column, table);
                genTableColumnMapper.insertGenTableColumn(column);
            }
        });

        List<GenTableColumn> delColumns = tableColumns.stream().filter(column -> !dbTableColumnNames.contains(column.getColumnName())).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(delColumns))
        {
            genTableColumnMapper.deleteGenTableColumns(delColumns);
        }
    }

    /**
     * 批量生成代码（下载方式）
     * 
     * @param tableNames 表数组
     * @return 数据
     */
    @Override
    public byte[] downloadCode(String[] tableNames)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        for (String tableName : tableNames)
        {
            generatorCode(tableName, zip);
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }

    /**
     * 查询表信息并生成代码
     */
    private void generatorCode(String tableName, ZipOutputStream zip)
    {
        // 查询表信息
        GenTable table = genTableMapper.selectGenTableByName(tableName);
        // 设置主子表信息
        setSubTable(table);
        // 设置主键列信息
        setPkColumn(table);

        VelocityInitializer.initVelocity();

        VelocityContext context = VelocityUtils.prepareContext(table);

        // 获取模板列表
        List<String> templates = VelocityUtils.getTemplateList(table.getTplCategory(), table.getBackEnd(), table.getFrontEnd());
        for (String template : templates)
        {
            // 渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, UTF8);
            tpl.merge(context, sw);
            try
            {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(VelocityUtils.getFileName(template, table)));
                IOUtils.write(sw.toString(), zip, UTF8);
                IOUtils.closeQuietly(sw);
				zip.flush();
                zip.closeEntry();
            }
            catch (IOException e)
            {
                log.error("渲染模板失败，表名：" + table.getTableName(), e);
            }
        }
    }

    /**
     * 修改保存参数校验
     * 
     * @param genTable 业务信息
     */
    @Override
    public void validateEdit(GenTable genTable)
    {
        if (GenConstants.TPL_TREE.equals(genTable.getTplCategory()))
        {
            String options = JSON.toJSONString(genTable.getParams());
            JSONObject paramsObj = JSONObject.parseObject(options);
            if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_CODE)))
            {
                throw new RuntimeException("树编码字段不能为空");
            }
            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_PARENT_CODE)))
            {
                throw new RuntimeException("树父编码字段不能为空");
            }
            else if (StringUtils.isEmpty(paramsObj.getString(GenConstants.TREE_NAME)))
            {
                throw new RuntimeException("树名称字段不能为空");
            }
            else if (GenConstants.TPL_SUB.equals(genTable.getTplCategory()))
            {
                if (StringUtils.isEmpty(genTable.getSubTableName()))
                {
                    throw new RuntimeException("关联子表的表名不能为空");
                }
                else if (StringUtils.isEmpty(genTable.getSubTableFkName()))
                {
                    throw new RuntimeException("子表关联的外键名不能为空");
                }
            }
        }
    }

    /**
     * 根据sql创建数据表
     *
     * @param sql
     * @return
     */
    @Override
    public boolean createNewTable(String sql) {
        return genTableMapper.createNewTable(sql) > 0;
    }

    /**
     * 导出数据字典
     * @param response
     * @param tableIds 选中数据库表
     */
    @Override
    public void exportToExcel(HttpServletResponse response, Long[] tableIds) {
        Workbook workbook = new XSSFWorkbook();
        try {
            if (StringUtils.isNotNull(tableIds)){
                for (Long tableId : tableIds) {
                    GenTable genTable = genTableMapper.selectGenTableById(tableId);
                    List<GenTableColumn> columns = genTableColumnMapper.selectGenTableColumnListByTableId(tableId);
                    GenerateDataDictUtils.generateDataDict(workbook, genTable, columns);
                }
                String fileName = "数据字典.xlsx";

                ServletOutputStream outputStream = response.getOutputStream();
                response.setHeader( "Content-Disposition", "attachment;filename="  + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setCharacterEncoding("utf-8");
                workbook.write(outputStream);
            }
        }catch (Exception e){
            log.error("导出Excel异常{}", e.getMessage());
        }finally {
            IOUtils.closeQuietly(workbook);
        }
    }

    /**
     *
     * @param genTable
     */
    @Override
    public void insertGenTable(GenTable genTable) {
        GenUtils.initTable(genTable, operName);
        genTable.setUpdateTime(new Date());
        int row = genTableMapper.insertGenTable(genTable);
        if (row > 0)
        {
            // 保存列信息
            List<GenTableColumn> genTableColumns = getTableColumns(genTable.getTableName());
            for (GenTableColumn column : genTableColumns)
            {
                GenUtils.initColumnField(column, genTable);
                genTableColumnMapper.insertGenTableColumn(column);
            }
        }
    }


    /**
     * 设置主键列信息
     * 
     * @param table 业务表信息
     */
    public void setPkColumn(GenTable table)
    {
        for (GenTableColumn column : table.getColumns())
        {
            if (column.isPk())
            {
                table.setPkColumn(column);
                break;
            }
        }
        if (StringUtils.isNull(table.getPkColumn()))
        {
            table.setPkColumn(table.getColumns().get(0));
        }
        if (GenConstants.TPL_SUB.equals(table.getTplCategory()))
        {
            for (GenTableColumn column : table.getSubTable().getColumns())
            {
                if (column.isPk())
                {
                    table.getSubTable().setPkColumn(column);
                    break;
                }
            }
            if (StringUtils.isNull(table.getSubTable().getPkColumn()))
            {
                table.getSubTable().setPkColumn(table.getSubTable().getColumns().get(0));
            }
        }
    }

    /**
     * 设置主子表信息
     * 
     * @param table 业务表信息
     */
    public void setSubTable(GenTable table)
    {
        String subTableName = table.getSubTableName();
        if (StringUtils.isNotEmpty(subTableName))
        {
            table.setSubTable(genTableMapper.selectGenTableByName(subTableName));
        }
    }

    /**
     * 设置代码生成其他选项值
     * 
     * @param genTable 设置后的生成对象
     */
    public void setTableFromOptions(GenTable genTable)
    {
        JSONObject paramsObj = JSONObject.parseObject(genTable.getOptions());
        if (StringUtils.isNotNull(paramsObj))
        {
            String treeCode = paramsObj.getString(GenConstants.TREE_CODE);
            String treeParentCode = paramsObj.getString(GenConstants.TREE_PARENT_CODE);
            String treeName = paramsObj.getString(GenConstants.TREE_NAME);
            String parentMenuName = paramsObj.getString(GenConstants.PARENT_MENU_NAME);
            
            genTable.setTreeCode(treeCode);
            genTable.setTreeParentCode(treeParentCode);
            genTable.setTreeName(treeName);
            genTable.setDataBase(getCurrentDataBase());
            genTable.setParentMenuName(parentMenuName);
        }
    }

    /**
     * 获取代码生成地址
     * 
     * @param table 业务表信息
     * @param template 模板文件路径
     * @return 生成地址
     */
    public static String getGenPath(GenTable table, String template)
    {
        String genPath = table.getGenPath();
        if (StringUtils.equals(genPath, "/"))
        {
            return System.getProperty("user.dir") + File.separator + "src" + File.separator + VelocityUtils.getFileName(template, table);
        }
        return genPath + File.separator + VelocityUtils.getFileName(template, table);
    }

    /**
     * 获取表字段信息（支持动态数据源）
     */
    private List<GenTableColumn> getTableColumns(String tableName) {
        Long currentDataSourceId = DatabaseSourceConfig.getCurrentDataSourceId();
        if (currentDataSourceId != null) {
            // 使用动态数据源查询
            return dataSourceSwitcher.selectDbTableColumnsByName(currentDataSourceId, tableName);
        } else {
            // 使用默认数据源查询
            return genTableColumnMapper.selectDbTableColumnsByName(tableName, getCurrentDataBase());
        }
    }
}