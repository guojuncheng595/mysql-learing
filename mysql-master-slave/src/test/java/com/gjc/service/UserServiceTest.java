package com.gjc.service;

import com.gjc.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void listUser() {
        List<User> users = userService.list();
        for (User user : users) {
            System.out.println(user.getUserId());
            System.out.println(user.getUserName());
            System.out.println(user.getUserPhone());
        }
    }

    @Test
    public void update() {
        userService.update();
        User user = userService.find();
        System.out.println(user.getUserName());
    }

}