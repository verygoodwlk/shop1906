package com.qf.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class MyGoodsStartFilterFactory extends AbstractGatewayFilterFactory {

    @Autowired
    private MyGoodsStartFilter myGoodsStartFilter;

    @Override
    public GatewayFilter apply(Object config) {
        return myGoodsStartFilter;
    }

    @Override
    public String name() {
        return "isStart";
    }
}
