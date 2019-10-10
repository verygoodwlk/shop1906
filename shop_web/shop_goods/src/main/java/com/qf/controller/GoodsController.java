package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询所有商品列表
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Goods> goodsList(){
        List<Goods> goods = goodsService.queryAllGoods();
        return goods;
    }

    /**
     * 商品添加
     * @param goods
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public boolean goodsInsert(@RequestBody Goods goods){
        System.out.println("商品服务接收到添加商品的请求：" + goods);
        int result = goodsService.insertGoods(goods);
        return result > 0;
    }
}
