package com.example.consumer9001.feignInter.myFallbackFactory;

import com.example.consumer9001.feignInter.FeignService1;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Date;


/**
 * feign使用断路器【熔断器】 ，当熔断发生后，运行这里的方法。类似于异常抛出
 * 这里主要是处理异常出错的情况(降级/熔断时服务不可用，fallback就会找到这里来)
 */
@Component  // 不要忘记添加，不要忘记添加,不加则无法使用熔断器
public class FeignServuce1FallbackFactory implements FallbackFactory<FeignService1> {
    @Override
    public FeignService1 create(Throwable throwable) {
        return new FeignService1() {
            @Override
            public String getname(String name) {
                //这里写熔断后的具体操作逻辑

                return "输入参数是" + name + "，feign使用了断路器【熔断器】，限制服务处于熔断状态，运行了类似于抛出异常的方法,时间=" + new Date();
            }
        };
    }
}



