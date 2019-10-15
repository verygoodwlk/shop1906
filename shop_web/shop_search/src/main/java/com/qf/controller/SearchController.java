package com.qf.controller;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * ElasticSearch -> Lucene
 */
@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private ISearchService searchService;

    /**
     * 根据关键字搜索索引库
     * @return
     */
    @RequestMapping("/keyword")
    public String searchByKeywork(String keyword, Model model){
        List<Goods> goods = searchService.query(keyword);
        model.addAttribute("goodsList", goods);
        return "searchlist";
    }

    /**
     * 添加商品到索引库
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public boolean insertSolr(@RequestBody Goods goods){
        return searchService.insert(goods);
    }
}
