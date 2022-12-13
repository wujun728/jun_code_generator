package com.freeter.entity;

/**
 * 表数据
 * 
 * @author xc
 * @email 171998110@qq.com
 * @date 2018年06月19日 上午11:13:16
 */
public class ReferencedTable {
	//表名
	private String tableName;
	//列名
	private String columnName;
	//关联表名
	private String referencedTableName;
	//关联列名
	private String referencedColumnName;
	
	//表名java
	private String tableNameJava;
	//列名java
	private String columnNameJava;
	//关联表名java
	private String referencedTableNameJava;
	//关联列名java
	private String referencedColumnNameJava;
 
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getReferencedTableName() {
		return referencedTableName;
	}

	public void setReferencedTableName(String referencedTableName) {
		this.referencedTableName = referencedTableName;
	}

	public String getReferencedColumnName() {
		return referencedColumnName;
	}

	public void setReferencedColumnName(String referencedColumnName) {
		this.referencedColumnName = referencedColumnName;
	}

	public String getTableNameJava() {
		return tableNameJava;
	}

	public void setTableNameJava(String tableNameJava) {
		this.tableNameJava = tableNameJava;
	}

	public String getColumnNameJava() {
		return columnNameJava;
	}

	public void setColumnNameJava(String columnNameJava) {
		this.columnNameJava = columnNameJava;
	}

	public String getReferencedTableNameJava() {
		return referencedTableNameJava;
	}

	public void setReferencedTableNameJava(String referencedTableNameJava) {
		this.referencedTableNameJava = referencedTableNameJava;
	}

	public String getReferencedColumnNameJava() {
		return referencedColumnNameJava;
	}

	public void setReferencedColumnNameJava(String referencedColumnNameJava) {
		this.referencedColumnNameJava = referencedColumnNameJava;
	}
	
	

}
