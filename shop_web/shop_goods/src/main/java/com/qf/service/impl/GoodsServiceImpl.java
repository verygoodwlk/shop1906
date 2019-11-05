package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.GoodsImageMapper;
import com.qf.dao.GoodsMapper;
import com.qf.dao.GoodsMiaoshaMapper;
import com.qf.entity.Goods;
import com.qf.entity.GoodsImage;
import com.qf.entity.GoodsMiaosha;
import com.qf.feign.ItemFeign;
import com.qf.feign.SearchFeign;
import com.qf.service.IGoodsService;
import com.qf.util.ContactUtil;
import com.qf.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsServiceImpl implements IGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsImageMapper goodsImageMapper;

    @Autowired
    private GoodsMiaoshaMapper goodsMiaoshaMapper;

    @Autowired
    private SearchFeign searchFeign;

    @Autowired
    private ItemFeign itemFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public List<Goods> queryAllGoods() {
        return goodsMapper.queryAllGoods();
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "miaosha", key = "'indexList'")
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

        //TODO 添加秒杀商品
        if (goods.getType() == 2){

            GoodsMiaosha miaosha = goods.getGoodsMiaosha();
            miaosha.setGid(goods.getId());

            //保存秒杀信息
            goodsMiaoshaMapper.insert(miaosha);

            //如果是秒杀商品，将信息保存到redis中

            //计算评分
            double score = TimeUtil.date2Score(goods.getGoodsMiaosha().getStartTime());

            //将商品信息保存到redis中
            stringRedisTemplate.opsForZSet().add(
                    ContactUtil.REDIS_MIAOSHA_SORT_SET, goods.getId() + "", score);

        }

        //将goods对象发送到指定的交换机中
        rabbitTemplate.convertAndSend("goods_exchange", goods.getType() == 1 ? "normal" : "miaosha", goods);

        return 1;
    }

    @Override
    public Goods queryById(Integer gid) {
        //查询商品的详细信息
        Goods goods = goodsMapper.selectById(gid);
        //查询商品的封面
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("gid", gid);
        queryWrapper.eq("isfengmian", 1);
        GoodsImage goodsImage = goodsImageMapper.selectOne(queryWrapper);

        //设置封面
        goods.setFengmian(goodsImage.getUrl());

        return goods;
    }

    @Override
    @Cacheable(cacheNames = "miaosha", key = "'indexList'")
    public List<Map<String, Object>> queryMiaoshaByTime() {

        System.out.println("查询了数据库中秒杀的商品信息！");

        List<Map<String, Object>> miaoshaList = new ArrayList<>();

        //获得当前时间 - SQL
        List<Goods> nowMiaoshaGoodsList = new ArrayList<>();
        List<GoodsMiaosha> nowMiaosha = goodsMiaoshaMapper.queryNow();
        if(nowMiaosha != null) {
            for (GoodsMiaosha goodsMiaosha : nowMiaosha) {
                //通过秒杀信息将商品信息查询出来
                Goods goods = queryById(goodsMiaosha.getGid());
                goods.setGoodsMiaosha(goodsMiaosha);
                nowMiaoshaGoodsList.add(goods);
            }
        }

        Map<String, Object> nowMap = new HashMap<>();
        nowMap.put("startTime", TimeUtil.getNow());
        nowMap.put("endTime", TimeUtil.getNextX(1));
        nowMap.put("goods", nowMiaoshaGoodsList);


        //获得下一场的秒杀
        List<Goods> nextMiaoshaGoodsList = new ArrayList<>();
        List<GoodsMiaosha> nextMiaosha = goodsMiaoshaMapper.queryNext();
        if(nextMiaosha != null){
            for (GoodsMiaosha goodsMiaosha : nextMiaosha) {
                //通过秒杀信息将商品信息查询出来
                Goods goods = queryById(goodsMiaosha.getGid());
                goods.setGoodsMiaosha(goodsMiaosha);
                nextMiaoshaGoodsList.add(goods);
            }
        }

        Map<String, Object> nextMap = new HashMap<>();
        nextMap.put("startTime", TimeUtil.getNextX(1));
        nextMap.put("endTime", TimeUtil.getNextX(2));
        nextMap.put("goods", nextMiaoshaGoodsList);

        miaoshaList.add(nowMap);
        miaoshaList.add(nextMap);

        return miaoshaList;
    }
}
