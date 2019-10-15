package com.qf.hystrix;

import com.qf.entity.Goods;
import com.qf.feign.SearchFeign;
import org.springframework.stereotype.Component;

@Component
public class SearchFeignHystrix implements SearchFeign {
    @Override
    public boolean insertSolr(Goods goods) {
        return false;
    }
}
