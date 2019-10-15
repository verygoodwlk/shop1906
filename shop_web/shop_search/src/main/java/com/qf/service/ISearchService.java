package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;

public interface ISearchService {

    boolean insert(Goods goods);

    List<Goods> query(String keyword);

}
