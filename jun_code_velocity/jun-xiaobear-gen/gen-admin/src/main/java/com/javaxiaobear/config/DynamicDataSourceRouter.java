package com.javaxiaobear.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 动态数据源路由器
 * 简化版本，只做路由不做动态创建
 *
 * @author javaxiaobear
 * @date 2025-01-08
 */
public class DynamicDataSourceRouter extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        Long dataSourceId = DatabaseSourceConfig.getCurrentDataSourceId();
        return dataSourceId != null ? dataSourceId : "default";
    }
}
