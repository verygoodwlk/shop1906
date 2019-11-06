package com.qf.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 验证秒杀商品是否开抢
 */
@Component
public class MyGoodsStartFilter implements GatewayFilter, Ordered {

    @Autowired
    private StringRedisTemplate redisTemplate;

    private String lua = "local gid = ARGV[1]\n" +
            "local profix = redis.call('get', 'time_profix')\n" +
            "local flag = redis.call('sismember', 'miaosha_start_'..profix, gid)\n" +
            "return flag";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("确认商品是否开抢过滤器触发.......");

        //获得商品id
        String gid = exchange.getRequest().getQueryParams().getFirst("gid");

        //执行lua脚本验证是否开抢
        //验证商品是否开抢
        Long result = redisTemplate.execute(new DefaultRedisScript<>(lua, Long.class), null, gid + "");

        if(result == 1){
            //已经可以抢了, 放行
            return chain.filter(exchange);
        }

        //商品未开抢
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.SEE_OTHER);
        String msg = null;
        try {
            msg = URLEncoder.encode("商品未开抢！", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        response.getHeaders().set("Location", "/info/error?msg=" + msg);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return 200;
    }
}
