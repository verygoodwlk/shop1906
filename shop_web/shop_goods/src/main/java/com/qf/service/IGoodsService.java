package com.qf.service;

import com.qf.entity.Goods;

import java.util.List;
import java.util.Map;

public interface IGoodsService {

    List<Goods> queryAllGoods();

    int insertGoods(Goods goods);

    Goods queryById(Integer gid);

    List<Map<String, Object>> queryMiaoshaByTime();
}
