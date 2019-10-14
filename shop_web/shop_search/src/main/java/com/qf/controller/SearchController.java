package com.qf.controller;

import com.qf.entity.Goods;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SolrClient solrClient;

    /**
     * 添加商品到索引库
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    public boolean insertSolr(@RequestBody Goods goods){

        SolrInputDocument solrDocument = new SolrInputDocument();
        solrDocument.addField("id", goods.getId() + "");
        solrDocument.addField("subject", goods.getSubject());
        solrDocument.addField("info", goods.getInfo());
        solrDocument.addField("price", goods.getPrice().doubleValue());
        solrDocument.addField("save",goods.getSave());

        //处理封面
        solrDocument.addField("images", goods.getFengmian());

        //添加到搜索库
        try {
            solrClient.add(solrDocument);
            solrClient.commit();

            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
