package com.example.ssm_mysql.dao;

import com.example.ssm_mysql.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User SelectUserByUsername(String username);
    boolean InsertUser(User user);
    boolean UpdateUser(User user);
    boolean DeleteUser(Integer id);
}
