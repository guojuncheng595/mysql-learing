package com.gjc.dydatasource;

import java.lang.annotation.*;

/**
 * @program: mysql-master-slave
 * @description: 数据主从库注解
 * @author: gjc
 * @create: 2020-08-31 11:48
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DataSourceSelector {

    DynamicDataSourceEnum value() default DynamicDataSourceEnum.MASTER;

    boolean clear() default true;
}
