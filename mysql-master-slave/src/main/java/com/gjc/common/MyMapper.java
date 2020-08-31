package com.gjc.common;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @program: mysql-master-slave
 * @description: my mapper
 * @author: gjc
 * @create: 2020-08-31 11:44
 **/
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    // TODO
    // FIXME 特别注意，该接口不能被扫描到，否则会出错
}
