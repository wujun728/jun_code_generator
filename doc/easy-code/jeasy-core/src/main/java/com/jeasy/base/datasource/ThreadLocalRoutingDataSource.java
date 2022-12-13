package com.jeasy.base.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class ThreadLocalRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceTypeManager.get();
    }

}
