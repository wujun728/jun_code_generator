package com.template.mapper;

import com.template.domain.DynamicDataSource;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 动态数据源 数据层
 *
 * @author Wujun
 */
@Mapper
public interface DynamicDataSourceMapper {

    List<DynamicDataSource> selectDynamicDataSourceList(DynamicDataSource dataSource);

    DynamicDataSource selectDynamicDataSourceById(Long id);

    int insertDynamicDataSource(DynamicDataSource dataSource);

    int updateDynamicDataSource(DynamicDataSource dataSource);

    int deleteDynamicDataSourceById(Long id);
}