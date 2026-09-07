package com.template.util;

import com.template.domain.GenTable;
import com.template.domain.GenTableColumn;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

/**
 * 数据字典生成工具
 *
 * @author Wujun
 */
public class GenerateDataDictUtils {

    public static void generateDataDict(Workbook workbook, GenTable genTable, List<GenTableColumn> columns) {
        Sheet sheet = workbook.createSheet(genTable.getTableName() + "-" + genTable.getTableComment());

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row headerRow = sheet.createRow(0);
        String[] headers = {"序号", "列名", "列描述", "数据类型", "Java类型", "Java属性", "主键", "必填", "插入", "编辑", "列表", "查询", "查询方式", "显示类型", "字典类型"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(headerStyle);
        }

        for (int i = 0; i < columns.size(); i++) {
            GenTableColumn col = columns.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(col.getColumnName());
            row.createCell(2).setCellValue(col.getColumnComment());
            row.createCell(3).setCellValue(col.getColumnType());
            row.createCell(4).setCellValue(col.getJavaType());
            row.createCell(5).setCellValue(col.getJavaField());
            row.createCell(6).setCellValue("1".equals(col.getIsPk()) ? "是" : "否");
            row.createCell(7).setCellValue("1".equals(col.getIsRequired()) ? "是" : "否");
            row.createCell(8).setCellValue("1".equals(col.getIsInsert()) ? "是" : "否");
            row.createCell(9).setCellValue("1".equals(col.getIsEdit()) ? "是" : "否");
            row.createCell(10).setCellValue("1".equals(col.getIsList()) ? "是" : "否");
            row.createCell(11).setCellValue("1".equals(col.getIsQuery()) ? "是" : "否");
            row.createCell(12).setCellValue(col.getQueryType());
            row.createCell(13).setCellValue(col.getHtmlType());
            row.createCell(14).setCellValue(col.getDictType());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}