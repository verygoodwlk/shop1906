package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("web-item")
public interface ItemFeign {

    @RequestMapping("/item/createHtml")
    String createHtml(@RequestBody Goods goods);
}
