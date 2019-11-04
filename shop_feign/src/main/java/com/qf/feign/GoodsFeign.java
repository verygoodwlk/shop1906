package com.qf.feign;

import com.qf.entity.Goods;
import com.qf.hystrix.GoodsFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "web-goods", fallback = GoodsFeignHystrix.class)
public interface GoodsFeign {

    @RequestMapping("/goods/list")
    List<Goods> goodsList();

    @RequestMapping("/goods/insert")
    boolean goodsInsert(@RequestBody Goods goods);

    @RequestMapping("/goods/queryById")
    Goods queryById(@RequestParam("gid") Integer gid);

    @RequestMapping("/goods/queryByTime")
    List<Map<String, Object>> queryMiaoshaByTime();
}
