package com.qf.controller;

import com.qf.entity.News;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    /**
     * 返回最新的新闻列表
     * @return
     */
    @RequestMapping("/list")
    public List<News> getNewsList(){

        List<News> newsList = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            News news = new News()
                    .setId(i)
                    .setTitle("今日新闻" + i)
                    .setInfo("今日新闻的简单介绍" + i)
                    .setFengmian("http://192.168.195.129:8080/group1/M00/00/06/wKjDgV3ItXSAUl5UAAY3M2H_7AI781.JPG");

            newsList.add(news);
        }

        return newsList;
    }
}
