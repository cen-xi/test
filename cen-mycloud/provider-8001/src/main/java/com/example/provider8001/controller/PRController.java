package com.example.provider8001.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PRController {

    @RequestMapping(value = "/getname",method = RequestMethod.GET)
    public String getname(String name){
        System.out.println("接收名字="+name);
        return "你大爷叫："+name;
    }

}
