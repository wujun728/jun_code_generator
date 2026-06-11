package com.javaxiaobear.service.impl;

import com.javaxiaobear.domain.DynamicDataSource;
import com.javaxiaobear.mapper.DynamicDataSourceMapper;
import com.javaxiaobear.service.IDynamicDataSourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 动态数据源配置Service业务层处理
 * 
 * @author javaxiaobear
 * @date 2025_01_08
 */
@Service
public class DynamicDataSourceServiceImpl implements IDynamicDataSourceService 
{
    @Resource
    private DynamicDataSourceMapper dynamicDataSourceMapper;

    /**
     * 查询动态数据源配置
     * 
     * @param id 动态数据源配置主键
     * @return 动态数据源配置
     */
    @Override
    public DynamicDataSource selectDynamicDataSourceById(Long id)
    {
        return dynamicDataSourceMapper.selectDynamicDataSourceById(id);
    }

    /**
     * 查询动态数据源配置列表
     * 
     * @param dynamicDataSource 动态数据源配置
     * @return 动态数据源配置
     */
    @Override
    public List<DynamicDataSource> selectDynamicDataSourceList(DynamicDataSource dynamicDataSource)
    {
        return dynamicDataSourceMapper.selectDynamicDataSourceList(dynamicDataSource);
    }

    /**
     * 新增动态数据源配置
     * 
     * @param dynamicDataSource 动态数据源配置
     * @return 结果
     */
    @Override
    public int insertDynamicDataSource(DynamicDataSource dynamicDataSource)
    {
        dynamicDataSource.setCreateTime(new Date());
        return dynamicDataSourceMapper.insertDynamicDataSource(dynamicDataSource);
    }

    /**
     * 修改动态数据源配置
     * 
     * @param dynamicDataSource 动态数据源配置
     * @return 结果
     */
    @Override
    public int updateDynamicDataSource(DynamicDataSource dynamicDataSource)
    {
        dynamicDataSource.setUpdateTime(new Date());
        return dynamicDataSourceMapper.updateDynamicDataSource(dynamicDataSource);
    }

    /**
     * 批量删除动态数据源配置
     * 
     * @param ids 需要删除的动态数据源配置主键
     * @return 结果
     */
    @Override
    public int deleteDynamicDataSourceByIds(Long[] ids)
    {
        return dynamicDataSourceMapper.deleteDynamicDataSourceByIds(ids);
    }

    /**
     * 删除动态数据源配置信息
     * 
     * @param id 动态数据源配置主键
     * @return 结果
     */
    @Override
    public int deleteDynamicDataSourceById(Long id)
    {
        return dynamicDataSourceMapper.deleteDynamicDataSourceById(id);
    }

    /**
     * 检查是否存在启用状态的数据源配置
     *
     * @return 是否存在启用的数据源
     */
    @Override
    public boolean hasEnabledDataSource()
    {
        DynamicDataSource query = new DynamicDataSource();
        query.setStatus(1); // 启用状态
        List<DynamicDataSource> list = dynamicDataSourceMapper.selectDynamicDataSourceList(query);
        return list != null && !list.isEmpty();
    }

    /**
     * 根据数据源名称查询数据源配置
     *
     * @param name 数据源名称
     * @return 数据源配置
     */
    @Override
    public DynamicDataSource selectDynamicDataSourceByName(String name)
    {
        DynamicDataSource query = new DynamicDataSource();
        query.setName(name);
        List<DynamicDataSource> list = dynamicDataSourceMapper.selectDynamicDataSourceList(query);
        return (list != null && !list.isEmpty()) ? list.get(0) : null;
    }
}
