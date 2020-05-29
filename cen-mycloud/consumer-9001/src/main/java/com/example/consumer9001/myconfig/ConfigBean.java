package com.example.consumer9001.myconfig;

import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

///注解@Configuration千万千万不要忘记添加 ，不然无法使用这个静态配置类
@Configuration
public class ConfigBean {

    //设置负载均衡策略
    @Bean
    public IRule myRule() {
        //其他看看 https://www.cnblogs.com/htyj/p/10705472.html
        //

        //轮询策略，其实里面就是一个计数器
//        return new RoundRobinRule();

        //该策略通过遍历负载均衡器中维护的所有实例，会过滤调故障的实例，并找出并发请求数最小的一个，所以该策略的特征是选择出最空闲的实例
        //如果集群有个服务器挂了，就可以过略的他，防止访问了故障服务器
        return new BestAvailableRule();

    }

}
