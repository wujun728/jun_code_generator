package com.jun.plugin.project.generatesql.entity;

import lombok.Data;

import java.util.List;

@Data
public class SqlTable {

    private String tableName;
    private String tableDesc;

    private List<ExcelRow> rows;

}
