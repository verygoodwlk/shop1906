package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 购物车的控制器
 * xxxxxxx
 * xxxxxx
 *
 * /cart/insert
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {


    /**
     * 添加购物车
     * gid - 商品id
     * gnumber - 商品数量
     *
     * 自定义注解 + AOP 实现后端的登录状态验证
     *
     * @return
     */
    @IsLogin
    @RequestMapping("/insert")
    public String insert(Integer gid, Integer gnumber, User user){
        System.out.println("添加购物车：" + gid + " " + gnumber);
        System.out.println("当前是否登录：" + user);

        //判断当前用户是否登录？

        return "succ";
    }
}
