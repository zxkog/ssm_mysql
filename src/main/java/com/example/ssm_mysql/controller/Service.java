package com.example.ssm_mysql.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Service {
    @RequestMapping(value = "NTP")
    public void aaa() {
        System.out.println("NTP");
    }
}
