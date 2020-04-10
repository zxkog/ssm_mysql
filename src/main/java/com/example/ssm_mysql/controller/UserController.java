package com.example.ssm_mysql.controller;

import com.example.ssm_mysql.dao.UserMapper;
import com.example.ssm_mysql.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserMapper usermapper;

    @RequestMapping(value = "/insert")
    public boolean insert(){
        User user = new User();
        user.setId(12);
        user.setUsername("zhang");
        user.setPassword("23456");
        user.setAge(23);
        return usermapper.InsertUser(user);
    }

    @RequestMapping(value = "/update")
    public boolean update() {
        User user = new User();
        user.setId(12);
        user.setUsername("zhangyu");
        user.setPassword("54321");
        user.setAge(23);
        return usermapper.UpdateUser(user);
    }

    @RequestMapping(value = "/select")
    public User select(){
        User user = usermapper.SelectUserByUsername("zhang");
        System.out.print("ok");
        return user;
    }

    /*** 根据  id  删除 数据*/
    @RequestMapping(value = "/delete/{id}")
    public boolean delete(@PathVariable("id") int id) {
        return usermapper.DeleteUser(id);
    }

}
