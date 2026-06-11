package com.javaxiaobear.service;

import com.javaxiaobear.domain.DynamicDataSource;

import java.util.List;

/**
 * 动态数据源配置Service接口
 * 
 * @author javaxiaobear
 * @date 2025_01_08
 */
public interface IDynamicDataSourceService 
{
    /**
     * 查询动态数据源配置
     * 
     * @param id 动态数据源配置主键
     * @return 动态数据源配置
     */
    public DynamicDataSource selectDynamicDataSourceById(Long id);

    /**
     * 查询动态数据源配置列表
     * 
     * @param dynamicDataSource 动态数据源配置
     * @return 动态数据源配置集合
     */
    public List<DynamicDataSource> selectDynamicDataSourceList(DynamicDataSource dynamicDataSource);

    /**
     * 新增动态数据源配置
     * 
     * @param dynamicDataSource 动态数据源配置
     * @return 结果
     */
    public int insertDynamicDataSource(DynamicDataSource dynamicDataSource);

    /**
     * 修改动态数据源配置
     * 
     * @param dynamicDataSource 动态数据源配置
     * @return 结果
     */
    public int updateDynamicDataSource(DynamicDataSource dynamicDataSource);

    /**
     * 批量删除动态数据源配置
     * 
     * @param ids 需要删除的动态数据源配置主键集合
     * @return 结果
     */
    public int deleteDynamicDataSourceByIds(Long[] ids);

    /**
     * 删除动态数据源配置信息
     * 
     * @param id 动态数据源配置主键
     * @return 结果
     */
    public int deleteDynamicDataSourceById(Long id);

    /**
     * 检查是否存在启用状态的数据源配置
     *
     * @return 是否存在启用的数据源
     */
    public boolean hasEnabledDataSource();

    /**
     * 根据数据源名称查询数据源配置
     *
     * @param name 数据源名称
     * @return 数据源配置
     */
    public DynamicDataSource selectDynamicDataSourceByName(String name);
}
