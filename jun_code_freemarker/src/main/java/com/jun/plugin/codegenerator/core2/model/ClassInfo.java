package com.jun.plugin.codegenerator.core2.model;

import lombok.Data;

import java.util.List;

@Data
public class ClassInfo {

    private String tableName;
    private String className;
	private String classComment;

	private List<FieldInfo> fieldList;


}