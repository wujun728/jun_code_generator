package com.freeter.entity;

import java.util.List;

/**
 * 表数据
 * 
 * @author xc
 * @email 171998110@qq.com
 * @date 2018年06月19日 上午10:12:59
 */
public class TableEntity {
	//表的名称
	private String tableName;
	//表的备注
	private String comments;
	//表的主键
	private ColumnEntity pk;
	//表的列名(不包含主键)
	private List<ColumnEntity> columns;
	
	//类名(第一个字母大写)，如：sys_user => SysUser
	private String className;
	//类名(第一个字母小写)，如：sys_user => sysUser
	private String classname;
	
	//判断是否存在
	private Boolean hasBigDecimal;
	
	//关联的字段
	private List<ReferencedTable> listReferencedTable;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public ColumnEntity getPk() {
		return pk;
	}
	public void setPk(ColumnEntity pk) {
		this.pk = pk;
	}
	public List<ColumnEntity> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnEntity> columns) {
		this.columns = columns;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	public Boolean getHasBigDecimal() {
		return hasBigDecimal;
	}
	public void setHasBigDecimal(Boolean hasBigDecimal) {
		this.hasBigDecimal = hasBigDecimal;
	}
	public List<ReferencedTable> getListReferencedTable() {
		return listReferencedTable;
	}
	public void setListReferencedTable(List<ReferencedTable> listReferencedTable) {
		this.listReferencedTable = listReferencedTable;
	}
	
	
}
