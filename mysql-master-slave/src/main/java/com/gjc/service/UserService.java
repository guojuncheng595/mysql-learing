package com.gjc.service;

import com.gjc.domain.User;
import com.gjc.dydatasource.DataSourceSelector;
import com.gjc.dydatasource.DynamicDataSourceEnum;
import com.gjc.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: mysql-master-slave
 * @description: 用户接口服务
 * @author: gjc
 * @create: 2020-08-31 11:44
 **/

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 从从库中读取信息
     *
     * @return
     */
    @DataSourceSelector(value = DynamicDataSourceEnum.SLAVE)
    public List<User> list() {
        List<User> users = userMapper.selectAll();
        return users;
    }

    /**
     * 从主库中写入信息
     */
    @DataSourceSelector(value = DynamicDataSourceEnum.MASTER)
    public int update() {
        User user = new User();
        user.setUserId(Long.parseLong("1196978513958141952"));
        user.setUserName("修改后的名字2");
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @DataSourceSelector(value = DynamicDataSourceEnum.SLAVE)
    public User find() {
        User user = new User();
        user.setUserId(Long.parseLong("1196978513958141952"));
        return userMapper.selectByPrimaryKey(user);
    }
}
