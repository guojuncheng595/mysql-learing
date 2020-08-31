package com.gjc.aop;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.gjc.dydatasource.DynamicDataSource;
import com.gjc.dydatasource.DynamicDataSourceEnum;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: mysql-master-slave
 * @description: 数据库主从配置
 * @author: gjc
 * @create: 2020/08/31 09:38
 **/

@Configuration
@MapperScan(basePackages = "com.gjc.mapper", sqlSessionTemplateRef = "sqlTemplate")
public class DataSourceConfig {

    /**
     * 主库
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDB() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 从库
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDB() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 主从动态配置
     */
    @Bean
    public DynamicDataSource dynamicDB(@Qualifier("masterDB") DataSource masterDataSource,
                                       @Autowired(required = false) @Qualifier("slaveDB") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DynamicDataSourceEnum.MASTER.getDataSourceName(), masterDataSource);
        if (slaveDataSource != null) {
            targetDataSources.put(DynamicDataSourceEnum.SLAVE.getDataSourceName(), slaveDataSource);
        }
        //
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDataSource;
    }

    /**
     * mybatis 相关配置
     *
     * @param dynamicDataSource
     * @return
     */
    @Bean
    public SqlSessionFactory sessionFactory(@Qualifier("dynamicDB") DataSource dynamicDataSource)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml"));
        bean.setDataSource(dynamicDataSource);
        return bean.getObject();
    }

    /**
     * 根据sql工厂返回sql模板
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlTemplate(@Qualifier("sessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理
     * @param dynamicDataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager dataSourceTx(@Qualifier("dynamicDB") DataSource dynamicDataSource){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dynamicDataSource);
        return dataSourceTransactionManager;
    }

}


