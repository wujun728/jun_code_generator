package com.jun.plugin.codegenerator.core2.model;


import lombok.Data;

import java.util.List;
@Data
public class Table {
	
	private String tableName;
    private String className;
	private String classComment;
	private List<FieldInfo> fieldList;

	private String sqlName;
	private String remarks;
	private String classNameLower;
	private List<Column> columnList ;
	 
}
