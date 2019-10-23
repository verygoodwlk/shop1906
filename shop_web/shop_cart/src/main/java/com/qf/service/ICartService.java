package com.qf.service;

import com.qf.entity.Shopcart;
import com.qf.entity.User;

import java.util.List;

public interface ICartService {

    /**
     * 添加购物车
     * @param gid
     * @param gnumber
     * @param user
     * @return
     */
    String insertCart(Integer gid, Integer gnumber, User user, String cartToken);

    /**
     * 查询所有购物车列表
     * @param cartToken
     * @param user
     * @return
     */
    List<Shopcart> queryShopCart(String cartToken, User user);

    /**
     * 合并购物车
     * @param cartToken
     * @param user
     * @return
     */
    int mergeShopCart(String cartToken, User user);

    /**
     * 根据商品id和用户id去购物车表中查询购物车信息
     * @param gid
     * @param uid
     * @return
     */
    List<Shopcart> queryByGid(Integer[] gid, Integer uid);

    List<Shopcart> queryByIds(Integer[] ids);

    int deleteByIds(Integer[] ids);
}
