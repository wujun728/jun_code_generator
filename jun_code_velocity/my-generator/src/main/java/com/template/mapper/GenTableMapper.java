package com.template.mapper;

import java.util.List;

import com.template.domain.GenTable;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 业务 数据层
 *
 * @author Wujun
 */
@Mapper
public interface GenTableMapper
{
    /**
     * 查询业务列表
     */
    List<GenTable> selectGenTableList(GenTable genTable);

    /**
     * 查询数据库列表
     */
    List<GenTable> selectDbTableList(GenTable genTable);

    /**
     * 查询数据库列表
     */
    List<GenTable> selectDbTableListByNames(@Param("array") String[] tableNames, @Param("dataBase") String dataBase);

    /**
     * 查询表名称信息
     */
    GenTable selectTableByName(@Param("tableName") String tableName, @Param("dataBase") String dataBase);

    /**
     * 查询所有表信息
     */
    List<GenTable> selectGenTableAll();

    /**
     * 查询表ID业务信息
     */
    GenTable selectGenTableById(Long id);

    /**
     * 根据表ID数组查询
     */
    List<GenTable> selectGenTableByIds(@Param("array") String[] tableIds);

    /**
     * 查询表名称业务信息
     */
    GenTable selectGenTableByName(String tableName);

    /**
     * 新增业务
     */
    int insertGenTable(GenTable genTable);

    /**
     * 根据SQL创建表
     */
    int createNewTable(@Param("sql") String sql);

    /**
     * 修改业务
     */
    int updateGenTable(GenTable genTable);

    /**
     * 批量删除业务
     */
    int deleteGenTableByIds(Long[] ids);
}
