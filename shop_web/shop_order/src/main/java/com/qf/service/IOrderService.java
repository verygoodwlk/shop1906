package com.qf.service;

import com.qf.entity.Orders;

import java.util.List;

public interface IOrderService {

    int insertOrder(Integer aid, Integer[] cartsid, Integer uid);

    List<Orders> queryByUid(Integer uid);

    Orders queryById(Integer oid);

    Orders queryByOrderId(String orderid);
}
