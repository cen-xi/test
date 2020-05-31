package com.example.configclient6080.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RefreshScope
@RestController
public class GetController {

    @Value("${yourname}")
    private String namestr;


    @RequestMapping(value = "/getname", method = RequestMethod.GET)
    public String getConfig() {

        String str = "获取远程配置文件信息：" + namestr + "===" + new Date();
        System.out.println(str);
        return str;
    }

//    http://localhost:6080/getname
}
