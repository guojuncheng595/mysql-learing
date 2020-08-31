package com.gjc.dydatasource;

import com.gjc.aop.DataSourceConfig;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @program: mysql-master-slave
 * @description: 数据库主从动态配置
 * @author: gjc
 * @create: 2020-08-31 09:50
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.get();
    }
}
