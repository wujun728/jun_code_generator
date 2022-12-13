package com.jeasy.common.excel;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
public class ExcelFiledInfo implements Comparable<ExcelFiledInfo> {
    
    private final Field field;
    private final String cellName;
    private final ExcelCellType type;
    private final String format;
    private final String el;
    private final int order;

    public ExcelFiledInfo(Field field, ExcelField excelField, int order) {
        this.field = field;
        this.cellName = excelField.value();
        this.type = excelField.type();
        this.format = excelField.format();
        this.el = excelField.el();
        this.order = excelField.order() == 0 ? order : excelField.order();
    }

    @Override
    public int compareTo(ExcelFiledInfo o) {
        int x = this.getOrder();
        int y = o.getOrder();
        return Integer.compare(x, y);
    }

    public Field getField() {
        return field;
    }

    public String getCellName() {
        return cellName;
    }

    public ExcelCellType getType() {
        return type;
    }

    public String getFormat() {
        return format;
    }

    public String getEl() {
        return el;
    }

    public int getOrder() {
        return order;
    }
}
