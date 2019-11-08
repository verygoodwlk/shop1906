package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.Goods;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 查询所有商品，连带将商品的图片信息一并查出
     * @return
     */
    List<Goods> queryAllGoods();

    /**
     * 减商品库存
     * @return
     */
    int jianSave(Integer gid);

}
