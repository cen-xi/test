package com.example.zuulserver5001.myProducerFallback;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

//注册bean
@Component
public class Consumer9001Fallback implements FallbackProvider {

    //测试请求1
//    http://localhost:5001/mzuul/consumer-9001?token=kk
    //测试请求2
//    http://localhost:5001/mzuul/consumer-9001/doname?token=kk&name=lili

    //指定要处理的远程服务
    @Override
    public String getRoute() {
        //必须是小写,即使 远程服务名的字符有大写，这里也必须换成小写，否则报错，无法执行服务降级操作，【与http网址一样，必须小写】
        return "consumer-9001";
    }

    /**
     * 具体退路操作逻辑
     */
    private ClientHttpResponse fallbackResponse(){
        return new ClientHttpResponse() {
            //返回http状态
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }
            //返回状态码
            @Override
            public int getRawStatusCode() throws IOException {
                //200是正常
                return 200;
            }
            //返回状态内容
            @Override
            public String getStatusText() throws IOException {
                return "OK";
            }

            //目前这里不清楚是干什么的
            @Override
            public void close() {

            }
            //返回响应体
            @Override
            public InputStream getBody() throws IOException {
                //以字节流形式返回
                return new ByteArrayInputStream("beng--i can do nothing,崩溃了,11223344".getBytes());
            }

            //返回响应头
            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders = new HttpHeaders();
                //设置响应数据的编码类型
                httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
                return httpHeaders;
            }
        };
    }



    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        if (cause != null && cause.getCause() != null) {
            String reason = cause.getCause().getMessage();
            System.out.println("异常原因：\n"+reason);
//            logger.info("Excption {}",reason);
        }

        return this.fallbackResponse();
    }
}


/*
总结：
（1）zuul使用服务列表的默认映射，那么在网址访问的时候，在路径写远程服务名时必须字符全小写【不论远程服务名字符是否有大写】，否则找不到服务；
（2）当远程服务异常，zuul调用该服务的熔断/降级操作，在回路操作设置时指定的远程服务名必须字符全小写【不论远程服务名字符是否有大写】，
     否则报错，无法执行该服务降级操作；
（3）使用 zuul --> 服务消费者 --> 服务提供者  的分布式微服务架构 。
    当提供者出现异常时，消费者对其服务降级，执行回路操作 ；
    但如果是zuul 路由到消费者去调用提供者服务，当提供者出现异常时，则将会执行zuul对消费者的服务降级，执行指定消费者的回路操作，
    【打印原因 java.net.SocketTimeoutException: Read timed out】
    但是过了一段时间后再次访问zuul 路由到消费者去调用该提供者服务，将会返回消费者对提供者执行的回路操作结果。
    出现这个现象的原因是 Zuul 路器熔断的超时时间小于远程服务消费者的feign熔断超时时间 导致的，修改Zuul的熔断超时时间即可。
    当消费者出现异常时，那么zuul将会执行对消费者的服务降级，执行该消费者的回路操作；


 */