package com.example.ssm_mysql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
@MapperScan("com.example.ssm_mysql.dao")
public class SsmMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsmMysqlApplication.class, args);


    }
}
