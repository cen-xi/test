package com.example.configserver6001;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
//开启发现服务  ，，也可以使用 EnableEurekaClient
@EnableDiscoveryClient
//开启配置中心服务端
@EnableConfigServer
public class ConfigServer6001Application {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServer6001Application.class, args);
    }

}
