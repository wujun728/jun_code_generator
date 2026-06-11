package com.javaxiaobear.mapper;

import java.util.List;
import com.javaxiaobear.domain.DynamicDataSource;

/**
 * 动态数据源配置Mapper接口
 * 
 * @author javaxiaobear
 * @date 2025_01_08
 */
public interface DynamicDataSourceMapper 
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
     * 删除动态数据源配置
     * 
     * @param id 动态数据源配置主键
     * @return 结果
     */
    public int deleteDynamicDataSourceById(Long id);

    /**
     * 批量删除动态数据源配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDynamicDataSourceByIds(Long[] ids);
}
