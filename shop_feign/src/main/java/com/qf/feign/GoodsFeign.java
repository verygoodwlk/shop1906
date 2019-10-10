package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("web-goods")
public interface GoodsFeign {

    @RequestMapping("/goods/list")
    List<Goods> goodsList();

    @RequestMapping("/goods/insert")
    boolean goodsInsert(@RequestBody Goods goods);
}
