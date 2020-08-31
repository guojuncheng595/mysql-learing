package com.gjc.dydatasource;

import lombok.Getter;

/**
 * @program: mysql-master-slave
 * @description: 数据源枚举类
 * @author: gjc
 * @create: 2020-08-31 10:01
 **/
@Getter
public enum DynamicDataSourceEnum {
    /**
     * 主库
     */
    MASTER("master"),
    /**
     * 从库
     */
    SLAVE("slave");

    private String dataSourceName;

    DynamicDataSourceEnum(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }
}
