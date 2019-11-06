package com.qf.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

/**
 * 将独立的过滤器注册到过滤器工厂中
 */
@Component
public class MyCodeFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    private MyCodeFilter myCodeFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return myCodeFilter;
    }

    /**
     * 设置过滤器的名称
     * @return
     */
    @Override
    public String name() {
        return "myCode";
    }
}
