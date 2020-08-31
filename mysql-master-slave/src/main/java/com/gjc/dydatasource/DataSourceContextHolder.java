package com.gjc.dydatasource;

/**
 * @program: mysql-master-slave
 * @description: 数据源上下文持有者
 * @author: gjc
 * @create: 2020-08-31 09:52
 **/
public class DataSourceContextHolder {
    private static final ThreadLocal<String> DYNAMIC_DATASOURCE_CONTEXT = new ThreadLocal<>();

    public static void set(String datasourceType){
        DYNAMIC_DATASOURCE_CONTEXT.set(datasourceType);
    }

    public static String get(){
        return DYNAMIC_DATASOURCE_CONTEXT.get();
    }

    public static void clear(){
        DYNAMIC_DATASOURCE_CONTEXT.remove();
    }
}
