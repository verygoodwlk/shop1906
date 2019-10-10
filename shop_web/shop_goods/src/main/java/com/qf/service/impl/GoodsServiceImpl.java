package com.qf.service.impl;

import com.qf.dao.GoodsImageMapper;
import com.qf.dao.GoodsMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsImage;
import com.qf.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Override
    public List<Goods> queryAllGoods() {
        return goodsMapper.selectList(null);
    }

    @Override
    @Transactional
    public int insertGoods(Goods goods) {

        //保存商品信息
        goodsMapper.insert(goods);

        //保存商品图片

        //封装一个封面的对象
        GoodsImage fengMian = new GoodsImage(
           goods.getId(),
           null,
           goods.getFengmian(),
           1
        );

        goodsImageMapper.insert(fengMian);

        //保存其他图片
        for (String otherUrl : goods.getOtherImg()) {
            GoodsImage otherImage = new GoodsImage(
                    goods.getId(),
                    null,
                    otherUrl,
                    0
            );

            goodsImageMapper.insert(otherImage);
        }

        return 1;
    }
}
