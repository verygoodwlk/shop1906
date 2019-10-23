package com.qf.util;

import com.qf.entity.Shopcart;

import java.math.BigDecimal;
import java.util.List;

public class PriceUtil {

    /**
     * 计算所有购物车信息的总价
     * @param shopcarts
     * @return
     */
    public static double allprice(List<Shopcart> shopcarts){

        //总价
        BigDecimal allprice = BigDecimal.valueOf(0);

        if(shopcarts != null){
            for (Shopcart shopcart : shopcarts) {

                //商品的单价
                BigDecimal pirce = shopcart.getGoods().getPrice();

                //购买数量
                BigDecimal number = BigDecimal.valueOf(shopcart.getNumber());

                //相乘
                BigDecimal multiply = pirce.multiply(number);

                //累加到总价格中
                allprice = allprice.add(multiply);
            }
        }

        return allprice.doubleValue();
    }
}
