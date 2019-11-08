package com.qf.service;

import com.qf.entity.Orders;

import java.util.List;

public interface IOrderService {

    Orders insertOrder(Integer aid, Integer[] cartsid, Integer uid);

    Orders insertMiaoshaOrder(Integer uid, Integer gid, String orderid);

    List<Orders> queryByUid(Integer uid);

    Orders queryById(Integer oid);

    Orders queryByOrderId(String orderid);

    int updateOrderState(String orderid, Integer status);
}
