package com.template.service;

import com.template.domain.DynamicDataSource;

import java.util.List;

/**
 * 动态数据源 服务层
 *
 * @author Wujun
 */
public interface IDynamicDataSourceService {

    List<DynamicDataSource> selectDynamicDataSourceList(DynamicDataSource dataSource);

    DynamicDataSource selectDynamicDataSourceById(Long id);

    int insertDynamicDataSource(DynamicDataSource dataSource);

    int updateDynamicDataSource(DynamicDataSource dataSource);

    int deleteDynamicDataSourceById(Long id);
}