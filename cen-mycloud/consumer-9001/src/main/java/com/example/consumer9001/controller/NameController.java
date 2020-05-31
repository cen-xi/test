package com.example.consumer9001.controller;


import com.example.consumer9001.feignInter.FeignService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RefreshScope
@RestController
public class NameController {
    @Autowired
    private FeignService1 feignService1;

    @RequestMapping(value = "/doname", method = RequestMethod.GET)
    public String doname(String name) {
        System.out.println("接收名字=" + name + "==" + new Date());
        return "我是消费者端口9001，微服务处理结果是：" + feignService1.getname(name);
    }


    @Value("${yourname}")
    private String namestr;

    @RequestMapping(value = "/getname", method = RequestMethod.GET)
    public String getConfig() {

        String str = "我是消费者端口9001，获取远程配置文件信息：" + namestr + "===" + new Date();
        System.out.println(str);
        return str;
    }

//    http://localhost:9001/getname

}
