package com.example.consumer9001;

import com.example.consumer9001.myconfig.ConfigBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//服务客户端【发现服务】
@EnableEurekaClient
//@EnableDiscoveryClient ,也可以使用这个
//指定feign接口扫描范围 ，也可以不写
@EnableFeignClients(basePackages = {"com.example.consumer9001.feignInter"})
//开启客户端负载均衡自定义策略，参数name是该服务器的应用名字 ，configuration设置 策略配置类
@RibbonClient(name = "consumer-9001" ,configuration = ConfigBean.class)
public class Consumer9001Application {

    public static void main(String[] args) {
        SpringApplication.run(Consumer9001Application.class, args);
    }

}
