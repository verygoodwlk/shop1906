package com.qf.controller;

import com.qf.feign.GoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsFeign goodsFeign;

    /**
     * 获取当前的秒杀商品信息
     * [{
     *     "startTime":"14:00:00",
     *     "endTime":"15:00:00",
     *     "goods":[{"subject":"", "fengmian":"", "goodsMiaosha":{}},{},{}]
     * },{
     *     "startTime":"15:00:00",
     *     "endTime":"16:00:00",
     *     "goods":[{"":"",},{}]
     * }]
     *
     *
     * @return
     */
    @RequestMapping("/queryByTime")
    @ResponseBody
    public List<Map<String, Object>> queryNowMiaosha(){
        List<Map<String, Object>> maps = goodsFeign.queryMiaoshaByTime();
        return maps;
    }

    /**
     * 获得服务器的当前时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/getNow")
    public Date getNow(){
        return new Date();
    }
}
