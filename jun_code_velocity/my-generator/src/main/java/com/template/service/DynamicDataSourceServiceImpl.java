package com.template.service;

import com.template.domain.DynamicDataSource;
import com.template.mapper.DynamicDataSourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 动态数据源 服务层实现
 *
 * @author Wujun
 */
@Service
public class DynamicDataSourceServiceImpl implements IDynamicDataSourceService {

    @Autowired
    private DynamicDataSourceMapper dynamicDataSourceMapper;

    @Override
    public List<DynamicDataSource> selectDynamicDataSourceList(DynamicDataSource dataSource) {
        return dynamicDataSourceMapper.selectDynamicDataSourceList(dataSource);
    }

    @Override
    public DynamicDataSource selectDynamicDataSourceById(Long id) {
        return dynamicDataSourceMapper.selectDynamicDataSourceById(id);
    }

    @Override
    public int insertDynamicDataSource(DynamicDataSource dataSource) {
        return dynamicDataSourceMapper.insertDynamicDataSource(dataSource);
    }

    @Override
    public int updateDynamicDataSource(DynamicDataSource dataSource) {
        return dynamicDataSourceMapper.updateDynamicDataSource(dataSource);
    }

    @Override
    public int deleteDynamicDataSourceById(Long id) {
        return dynamicDataSourceMapper.deleteDynamicDataSourceById(id);
    }
}