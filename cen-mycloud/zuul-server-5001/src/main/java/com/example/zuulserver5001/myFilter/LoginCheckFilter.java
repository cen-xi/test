package com.example.zuulserver5001.myFilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Filter;

@Component
public class LoginCheckFilter extends ZuulFilter {
    //上下文,不可以设为 private
    RequestContext ctx = null;
    //Request
    private HttpServletRequest request = null;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        System.out.println("进入zuul拦截-loginCheck拦截，判断是否开启该拦截");
        //获取上下文
        ctx = RequestContext.getCurrentContext();
//        判断上一层拦截是否通过
        if(!ctx.sendZuulResponse()){
            //上一层拦截不通过
            System.out.println(" 上一层拦截不通过,不开启loginCheck拦截");
            //该拦截不需要开启
            return false;
        }
        //上层拦截通过
        //获取Request
        request = ctx.getRequest();
        //获取请求路径
        String urlStr = request.getRequestURI().toString();
        //当访问路径含有/mzuul/bd/则开启该拦截
        return urlStr.contains("/mzuul/bd");
    }

    @Override
    public Object run() throws ZuulException {
        System.out.println("运行loginCheck拦截逻辑");
        //获取请求参数
        String token = request.getParameter("token");
        System.out.println("---参数token=" + token);
        if (StringUtils.isBlank(token) | (token != null && !token.equals("kk"))) {
//            token 是空的 或者 不是 kk
            //拦截
            System.out.println("拦截，拒绝路由请求， token 是空的 或者 不是 kk");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(7781);
            try {
                ctx.getResponse().setContentType("text/html;charset=UTF-8");
                ctx.getResponse().getWriter().write("请求参数="+token+",当参数是kk才可以通过");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
