package com.qf.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 验证码过滤器
 */
@Component
public class MyCodeFilter implements GatewayFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;
    
    /**
     * 过滤的方法
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("验证码过滤器触发.......");

        //过滤
        //获得请求的cookie
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        HttpCookie codeToken = cookies.getFirst("code_token");

        if(codeToken != null && codeToken.getValue() != null){
            //获得验证码的token

            //通过codetoken获得服务器暂存的code
            String serverCode = redisTemplate.opsForValue().get(codeToken.getValue());

            //和请求传输的验证码进行校验
            String code = request.getQueryParams().getFirst("code");

            //对比
            if(code != null && code.equals(serverCode)){
                System.out.println("验证码验证通过！！！！");
                //验证码验证通过
                redisTemplate.delete(codeToken.getValue());
                return chain.filter(exchange);//放行
            }
        }

        //验证码验证未通过
        System.out.println("验证码验证没有通过！！！！");
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);

        String msg = null;
        try {
            msg = URLEncoder.encode("验证码错误！", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        response.getHeaders().set("Location", "/info/error?msg=" + msg);
        return response.setComplete();
    }

    /**
     * 执行顺序，值越小优先级越大
     * @return
     */
    @Override
    public int getOrder() {
        return 100;
    }
}
