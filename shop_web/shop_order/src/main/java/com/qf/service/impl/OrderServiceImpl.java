package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.OrderDetilMapper;
import com.qf.dao.OrdersMapper;
import com.qf.entity.*;
import com.qf.feign.CartFeign;
import com.qf.feign.GoodsFeign;
import com.qf.service.IAddressService;
import com.qf.service.IOrderService;
import com.qf.util.PriceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetilMapper orderDetilMapper;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private CartFeign cartFeign;

    @Autowired
    private GoodsFeign goodsFeign;

    @Override
    @Transactional
    public Orders insertOrder(Integer aid, Integer[] cartsid, Integer uid) {

        //通过aid找到收货地址信息
        Address address = addressService.queryById(aid);

        //通过购物车id列表查询所有购物车信息
        List<Shopcart> shopcarts = cartFeign.queryByIds(cartsid);

        //构造订单对象
        Orders orders = (Orders) new Orders()
                .setOrderid(UUID.randomUUID().toString())
                .setUid(uid)
                .setPerson(address.getPerson())
                .setAddress(address.getAddress())
                .setCode(address.getCode())
                .setPhone(address.getPhone())
                .setAllprice(BigDecimal.valueOf(PriceUtil.allprice(shopcarts)))
                .setCreateTime(new Date())
                .setStatus(0);

        //添加订单
        ordersMapper.insert(orders);

        //构造订单详情对象
        for (Shopcart shopcart : shopcarts) {
            //构建订单详情对象
            OrderDetils orderDetils = (OrderDetils) new OrderDetils()
                    .setOid(orders.getId())
                    .setSubject(shopcart.getGoods().getSubject())
                    .setPrice(shopcart.getGoods().getPrice())
                    .setNumber(shopcart.getNumber())
                    .setGid(shopcart.getGid())
                    .setDetilsPrice(BigDecimal.valueOf(shopcart.getNumber()).multiply(shopcart.getGoods().getPrice()))
                    .setCreateTime(new Date())
                    .setStatus(0);

            orderDetilMapper.insert(orderDetils);
        }

        //删除购物车
        cartFeign.deleteByIds(cartsid);

        return orders;
    }

    @Override
    public List<Orders> queryByUid(Integer uid) {
        //根据用户id查询所有订单
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        queryWrapper.orderByDesc("create_time");
        List<Orders> list = ordersMapper.selectList(queryWrapper);

        //根据订单查询订单详情
        for (Orders orders : list) {
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("oid", orders.getId());
            List<OrderDetils> detilsList = orderDetilMapper.selectList(queryWrapper2);

            //根据订单详情查询商品信息
            for (OrderDetils orderDetils : detilsList) {
                Goods goods = goodsFeign.queryById(orderDetils.getGid());
                orderDetils.setGoods(goods);
            }

            orders.setOrderDetils(detilsList);
        }

        return list;
    }

    @Override
    public Orders queryById(Integer oid) {
        return ordersMapper.selectById(oid);
    }

    @Override
    public Orders queryByOrderId(String orderid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("orderid", orderid);
        return ordersMapper.selectOne(queryWrapper);
    }

    @Override
    public int updateOrderState(String orderid, Integer status) {

        Orders orders = this.queryByOrderId(orderid);
        orders.setStatus(status);
        return ordersMapper.updateById(orders);
    }
}
