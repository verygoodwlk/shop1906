package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.CartMapper;
import com.qf.entity.Goods;
import com.qf.entity.Shopcart;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import com.qf.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private GoodsFeign goodsFeign;

    @Override
    public String insertCart(Integer gid, Integer gnumber, User user, String cartToken) {

        Shopcart shopcart = new Shopcart(
                user != null ? user.getId() : null,
                gid,
                gnumber,
                null,
                null);

        //判定是否登录
        if(user != null){
            //说明已经登录
            //保存数据库
            cartMapper.insert(shopcart);

        } else {
            //说明未登录
            //保存到redis
            cartToken = cartToken == null ? UUID.randomUUID().toString() : cartToken;
            redisTemplate.opsForList().leftPush(cartToken, shopcart);
            redisTemplate.expire(cartToken, 365, TimeUnit.DAYS);
            return cartToken;
        }

        return null;
    }

    @Override
    public List<Shopcart> queryShopCart(String cartToken, User user) {

        List<Shopcart> shopcarts = null;

        if(user != null){
            //已经登录
            //从数据库查询所有商品信息
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("uid", user.getId());
            queryWrapper.orderByDesc("create_time");
            shopcarts = cartMapper.selectList(queryWrapper);
        } else {
            //未登录
            if(cartToken != null){
                //获得临时购物车的长度
                Long size = redisTemplate.opsForList().size(cartToken);
                //获得所有购物车列表
                shopcarts = redisTemplate.opsForList().range(cartToken, 0, size);
            }
        }

        //根据购物车信息查询商品的详细信息
        if(shopcarts != null) {
            for (Shopcart shopcart : shopcarts) {
                //调用商品服务查询商品的信息
                Goods goods = goodsFeign.queryById(shopcart.getGid());
                shopcart.setGoods(goods);
            }
        }

        return shopcarts;
    }

    @Override
    public int mergeShopCart(String cartToken, User user) {

        if(cartToken != null){
            //获取临时购物车
            //获得临时购物车的长度
            Long size = redisTemplate.opsForList().size(cartToken);
            //获得所有购物车列表
            List<Shopcart> shopcarts = redisTemplate.opsForList().range(cartToken, 0, size);

            //循环将临时购物车添加到永久购物车中
            for (Shopcart shopcart : shopcarts) {
                shopcart.setUid(user.getId());
                //保存进数据库中
                cartMapper.insert(shopcart);
            }

            //删除redis
            redisTemplate.delete(cartToken);

            return 1;
        }

        return 0;
    }
}
