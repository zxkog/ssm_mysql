package com.example.ssm_mysql.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/device")
public class Device {
    @RequestMapping(value = "/collect",method = RequestMethod.POST)
    public String DeviceCollect() {
        System.out.println("/device/collect");
        return "Hello from /device/collect";
    }
}
