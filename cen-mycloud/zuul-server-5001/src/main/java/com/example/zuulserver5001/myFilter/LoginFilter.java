package com.example.zuulserver5001.myFilter;


import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

//注册bean ，不使用@Component注解则需要去启动类创建一个方法new一个LoginFilter类后
// 使用 @bean注解注册bean，一样的作用
@Component
public class LoginFilter extends ZuulFilter {


    /**
     * 选择过滤器类型
     */
    @Override
    public String filterType() {
        //一共有下面4种过滤器
//        public static final String ERROR_TYPE = "error";
//        public static final String POST_TYPE = "post";
//        public static final String PRE_TYPE = "pre";
//        public static final String ROUTE_TYPE = "route";
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 通过返回的int值来定义过滤器的执行顺序，数字越小优先级越高
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 返回一个`Boolean`值，判断该过滤器是否需要执行。返回true执行，返回false不执行。
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器的具体业务逻辑
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("进入zuul拦截-login拦截");
        //获取上下文
        RequestContext ctx = RequestContext.getCurrentContext();
        //获取Request
        HttpServletRequest request = ctx.getRequest();
        //获取请求参数
        String token = request.getParameter("token");
        System.out.println("参数token=" + token);
//
//
        if (StringUtils.isBlank(token)) {
            //参数内容为空
            //拦截，拒绝路由
            ctx.setSendZuulResponse(false);
            //返回状态码
            ctx.setResponseStatusCode(401);
            //返回的响应体信息
            try {
                //不可以直接写中文，前端会显示中文乱码,加上这就解决中文乱码问题
                //以文本格式显示，字体比较大
                ctx.getResponse().setContentType("text/html;charset=UTF-8");
                //以json格式显示，字体比较小
//                ctx.getResponse().setContentType("application/json;charset=UTF-8");
//               上一句等同于  ctx.getResponse().setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                //
                ctx.getResponse().getWriter().write("token is 空的-------401");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
