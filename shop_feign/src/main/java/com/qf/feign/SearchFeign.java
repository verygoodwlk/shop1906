package com.qf.feign;

import com.qf.entity.Goods;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("web-search")
public interface SearchFeign {

    @RequestMapping("/search/insert")
    boolean insertSolr(@RequestBody Goods goods);
}
