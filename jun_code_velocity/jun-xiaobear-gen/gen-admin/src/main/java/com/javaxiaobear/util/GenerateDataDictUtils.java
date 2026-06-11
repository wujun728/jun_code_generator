package com.javaxiaobear.util;

import com.javaxiaobear.domain.GenTable;
import com.javaxiaobear.domain.GenTableColumn;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author javaxiaobear
 * @version 2.0
 * @description: 生成数据字典工具类
 * @date 2022/10/8 10:30
 */
public class GenerateDataDictUtils {

    /**
     * 生成数据字典
     * @param workbook
     * @param genTable
     * @param columns
     */
    public static void generateDataDict(Workbook workbook, GenTable genTable, List<GenTableColumn> columns){
        Sheet sheet = workbook.createSheet(genTable.getTableName());
        Row row = sheet.createRow(0);
        sheet.addMergedRegion(new CellRangeAddress(0,0,3,6));

        sheet.setDefaultColumnWidth(10);
        sheet.setColumnWidth(1,22*256);
        sheet.setColumnWidth(2,13*256);
        sheet.setColumnWidth(6,13*256);
        sheet.setColumnWidth(7,102*256);
        sheet.setColumnWidth(8,15*256);

        // 设置内容框线
        CellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setBorderBottom(BorderStyle.THIN);
        contentStyle.setBorderTop(BorderStyle.THIN);
        contentStyle.setBorderLeft(BorderStyle.THIN);
        contentStyle.setBorderRight(BorderStyle.THIN);

        row.createCell(0).setCellValue("表名");
        row.getCell(0).setCellStyle(contentStyle);
        row.createCell(1).setCellValue(genTable.getTableName());
        row.getCell(1).setCellStyle(contentStyle);
        row.createCell(2).setCellValue("名称");
        row.getCell(2).setCellStyle(contentStyle);
        row.createCell(3).setCellValue(genTable.getTableComment());
        row.getCell(3).setCellStyle(contentStyle);
        row.createCell(4);
        row.createCell(5);
        row.createCell(6);
        row.getCell(4).setCellStyle(contentStyle);
        row.getCell(5).setCellStyle(contentStyle);
        row.getCell(6).setCellStyle(contentStyle);

        row.createCell(7).setCellValue("类型");
        row.getCell(7).setCellStyle(contentStyle);
        row.createCell(8).setCellValue("");
        row.getCell(8).setCellStyle(contentStyle);

        Row row1 = sheet.createRow(1);
        sheet.addMergedRegion(new CellRangeAddress(1,1,1,8));
        row1.createCell(0).setCellValue("说明");
        row1.getCell(0).setCellStyle(contentStyle);
        row1.createCell(1).setCellStyle(contentStyle);
        row1.getCell(1).setCellValue(genTable.getFunctionName());
        row1.createCell(8).setCellStyle(contentStyle);

        Row row2 = sheet.createRow(2);
        //序号	字段名	类型	长度	值域	是否为空	说明	备注	默认值
        String[] titles = new String[]{"序号","字段名","类型","长度","是否为空", "说明", "备注", "默认值"};
        Cell cell;
        for (int i = 0; i < titles.length; i++) {
            cell = row2.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(contentStyle);
            CellStyle cellStyle = cell.getCellStyle();
            cellStyle.setFillBackgroundColor( IndexedColors.SKY_BLUE.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }
        int rowIndex = 3;
        Row rowTemp;
        int id = 1;
        for (GenTableColumn column : columns) {
            rowTemp = sheet.createRow(rowIndex);
            cell = rowTemp.createCell(0);
            cell.setCellValue(id);
            cell.setCellStyle(contentStyle);

            cell = rowTemp.createCell(1);
            cell.setCellValue(column.getColumnName());
            cell.setCellStyle(contentStyle);

            cell = rowTemp.createCell(2);
            cell.setCellValue(column.getColumnType().split("\\(")[0]);
            cell.setCellStyle(contentStyle);

            cell = rowTemp.createCell(3);
            cell.setCellValue(getTypeLength(column.getColumnType()));
            cell.setCellStyle(contentStyle);


            cell = rowTemp.createCell(4);
            cell.setCellValue("1".equals(column.getIsRequired()) ? "否" : "是");
            cell.setCellStyle(contentStyle);

            cell = rowTemp.createCell(5);
            cell.setCellValue(column.getColumnComment().split("\\（")[0]);
            cell.setCellStyle(contentStyle);

            cell = rowTemp.createCell(6);
            cell.setCellValue(getRemark(column.getColumnComment()));
            cell.setCellStyle(contentStyle);

            cell = rowTemp.createCell(7);
            cell.setCellValue("");
            cell.setCellStyle(contentStyle);

            id++;
            rowIndex++;
        }
    }


    /**
     * 获取备注 提取中文括号内容
     * @param type
     * @return
     */
    public static String getRemark(String type){
        String skh ="(\\[|（).*(\\]|）)";
        Pattern pattern= Pattern.compile(skh);
        Matcher matcher=pattern.matcher(type);
        boolean is=matcher.find();
        if(is){
            return matcher.group().substring(1, matcher.group().length() - 1);
        }else {
            return "";
        }
    }

    /**
     * 获取类型长度
     * @param type /（(\S*)）/
     * @return
     */
    public static String getTypeLength(String type){
        String skh ="\\((.*?)\\)";
        Pattern pattern= Pattern.compile(skh);
        Matcher matcher=pattern.matcher(type);
        boolean is=matcher.find();
        if(is){
            return matcher.group();
        }else {
            return "";
        }
    }
}
