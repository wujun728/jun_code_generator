package com.template.controller;

import com.template.config.DatabaseSourceConfig;
import com.template.domain.AjaxResult;
import com.template.domain.DynamicDataSource;
import com.template.domain.GenTable;
import com.template.service.IDynamicDataSourceService;
import com.template.service.IGenTableService;
import com.template.util.DataSourceSwitcher;
import com.template.util.FileDownloadUtils;
import io.github.pomzwj.dbexport.core.DataBaseExportExecute;
import io.github.pomzwj.dbexport.core.domain.DbExportConfig;
import io.github.pomzwj.dbexport.core.type.ExportFileType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 数据库文档导出控制器
 *
 * @author Wujun
 */
@RestController
@RequestMapping("/dev-api/gen")
public class DatabaseDictionaryExportController extends BaseController {

    @Autowired
    private IGenTableService genTableService;

    @Autowired
    private IDynamicDataSourceService dynamicDataSourceService;

    @Autowired
    private DatabaseSourceConfig databaseSourceConfig;

    @Autowired
    private DataSourceSwitcher dataSourceSwitcher;

    /**
     * 预览当前数据源的数据库表列表
     */
    @GetMapping("/preview/tables")
    public AjaxResult previewTables(@RequestParam(value = "dataSourceId", required = false) Long dataSourceId) {
        try {
            if (dataSourceId != null) {
                DatabaseSourceConfig.setCurrentDataSourceId(dataSourceId);
            }
            List<GenTable> tableList = dataSourceSwitcher.selectDbTableList(
                DatabaseSourceConfig.getCurrentDataSourceId(), null);
            return success(tableList);
        } catch (Exception e) {
            logger.error("预览数据库表失败", e);
            return error("预览数据库表失败：" + e.getMessage());
        } finally {
            if (dataSourceId != null) {
                DatabaseSourceConfig.clearCurrentDataSourceId();
            }
        }
    }

    /**
     * 导出数据库文档
     */
    @PostMapping("/db/export")
    public AjaxResult exportDatabase(@RequestParam(value = "tableNames", required = false) String tableNames,
                                   @RequestParam(value = "exportType", defaultValue = "EXCEL") String exportType,
                                   @RequestParam(value = "dataSourceId", required = false) Long dataSourceId) {
        try {
            if (dataSourceId != null) {
                DatabaseSourceConfig.setCurrentDataSourceId(dataSourceId);
            }

            DataSource dataSource = getCurrentDataSource();
            if (dataSource == null) {
                return error("无法获取数据源");
            }

            DbExportConfig dbExportConfig = new DbExportConfig();

            ExportFileType fileType;
            String fileExtension;
            switch (exportType.toUpperCase()) {
                case "WORD":
                    fileType = ExportFileType.WORD;
                    fileExtension = "docx";
                    break;
                case "PDF":
                    fileType = ExportFileType.PDF;
                    fileExtension = "pdf";
                    break;
                case "HTML":
                    fileType = ExportFileType.HTML;
                    fileExtension = "html";
                    break;
                case "MARKDOWN":
                    fileType = ExportFileType.MARKDOWN;
                    fileExtension = "md";
                    break;
                case "EXCEL":
                default:
                    fileType = ExportFileType.WORD;
                    fileExtension = "docx";
                    break;
            }

            dbExportConfig.setExportFileTypeEnum(fileType);

            if (tableNames != null && !tableNames.trim().isEmpty()) {
                List<String> tableList = Arrays.asList(tableNames.split(","));
                dbExportConfig.setSelectTableList(tableList);
            }

            String tempDir = System.getProperty("java.io.tmpdir");
            dbExportConfig.setGenerationFileTempDir(tempDir);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = "数据库设计文档_" + timestamp + "." + fileExtension;

            String filePath = DataBaseExportExecute.executeFile(dataSource, dbExportConfig);

            Map<String, Object> result = new HashMap<>();
            result.put("filePath", filePath);
            result.put("fileName", fileName);
            result.put("fileSize", new File(filePath).length());

            return AjaxResult.success("文档导出成功", result);

        } catch (Exception e) {
            logger.error("导出数据库文档失败", e);
            return error("导出数据库文档失败：" + e.getMessage());
        } finally {
            if (dataSourceId != null) {
                DatabaseSourceConfig.clearCurrentDataSourceId();
            }
        }
    }

    /**
     * 下载导出的文件（直接响应流）
     */
    @GetMapping("/db/download")
    public void downloadFileStream(@RequestParam("filePath") String filePath,
                                 @RequestParam("fileName") String fileName,
                                 HttpServletResponse response) {
        try {
            FileDownloadUtils.downloadFile(filePath, fileName, response);
            new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    FileDownloadUtils.deleteTempFile(filePath);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } catch (Exception e) {
            logger.error("文件下载失败", e);
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "文件下载失败");
            } catch (Exception ex) {
                logger.error("发送错误响应失败", ex);
            }
        }
    }

    /**
     * 获取支持的导出格式
     */
    @GetMapping("/db/formats")
    public AjaxResult getExportFormats() {
        List<Map<String, Object>> formats = new ArrayList<>();

        Map<String, Object> excel = new HashMap<>();
        excel.put("type", "EXCEL");
        excel.put("name", "Excel文档");
        excel.put("extension", "xlsx");
        formats.add(excel);

        Map<String, Object> word = new HashMap<>();
        word.put("type", "WORD");
        word.put("name", "Word文档");
        word.put("extension", "docx");
        formats.add(word);

        Map<String, Object> pdf = new HashMap<>();
        pdf.put("type", "PDF");
        pdf.put("name", "PDF文档");
        pdf.put("extension", "pdf");
        formats.add(pdf);

        Map<String, Object> html = new HashMap<>();
        html.put("type", "HTML");
        html.put("name", "HTML文档");
        html.put("extension", "html");
        formats.add(html);

        return success(formats);
    }

    /**
     * 获取当前数据源
     */
    private DataSource getCurrentDataSource() {
        try {
            Long currentDataSourceId = DatabaseSourceConfig.getCurrentDataSourceId();
            if (currentDataSourceId != null) {
                DynamicDataSource dynamicDataSource = dynamicDataSourceService.selectDynamicDataSourceById(currentDataSourceId);
                if (dynamicDataSource != null) {
                    return databaseSourceConfig.getDataSourceByConfig(dynamicDataSource);
                }
            }
            return databaseSourceConfig.createDefaultDataSource();
        } catch (Exception e) {
            logger.error("获取数据源失败", e);
            return null;
        }
    }
}