package com.qf.feign;

import com.qf.entity.Shopcart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("web-cart")
public interface CartFeign {

    @RequestMapping("/cart/queryByGid")
    List<Shopcart> queryByGid(
            @RequestParam("gid") Integer[] gid,
            @RequestParam("uid") Integer uid);

    @RequestMapping("/cart/queryByIds")
    List<Shopcart> queryByIds(@RequestParam("ids") Integer[] ids);


    @RequestMapping("/cart/deleteByIds")
    boolean deleteByIds(@RequestParam("ids") Integer[] ids);
}
