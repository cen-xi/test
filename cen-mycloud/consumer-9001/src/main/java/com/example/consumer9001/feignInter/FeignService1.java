package com.example.consumer9001.feignInter;

import com.example.consumer9001.feignInter.myFallbackFactory.FeignServuce1FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//注入远程服务的应用名【不区分大小写】 ，以及熔断回调类
@FeignClient(name = "provider-8001", fallbackFactory = FeignServuce1FallbackFactory.class )
public interface FeignService1 {


//    对应远程服务具体接口的名称和参数
    @RequestMapping(value = "/getname", method = RequestMethod.GET)
//    需要添加 @RequestParam ，用于纠正参数映射 ，不然会报错 405 ，
//    feign.FeignException$MethodNotAllowed: status 405 reading
    public String getname(@RequestParam("name") String name);


}
