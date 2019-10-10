package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.feign.GoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/goodsManager")
public class GoodsController {

    @Autowired
    private GoodsFeign goodsFeign;

    /**
     * 获取商品列表
     * @return
     */
    @RequestMapping("/list")
    public String goodsList(Model model){
        //调用商品服务获得商品列表
        List<Goods> goods = goodsFeign.goodsList();
        System.out.println("调用商品服务，获得商品列表：" + goods);
        model.addAttribute("goodsList", goods);
        return "goodslist";
    }
}
