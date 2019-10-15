package com.qf.hystrix;

import com.qf.entity.Goods;
import com.qf.feign.GoodsFeign;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GoodsFeignHystrix implements GoodsFeign {
    @Override
    public List<Goods> goodsList() {
        return null;
    }

    @Override
    public boolean goodsInsert(Goods goods) {
        return false;
    }
}
