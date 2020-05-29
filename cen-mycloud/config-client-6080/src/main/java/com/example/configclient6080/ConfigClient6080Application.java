package com.example.configclient6080;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication

//开启发现服务 ，也可以使用 EnableEurekaClient
@EnableDiscoveryClient
public class ConfigClient6080Application {

    public static void main(String[] args) {
        SpringApplication.run(ConfigClient6080Application.class, args);
    }

}
