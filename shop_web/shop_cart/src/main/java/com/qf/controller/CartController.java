package com.qf.controller;

import com.qf.aop.IsLogin;
import com.qf.entity.Shopcart;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 购物车的控制器
 *
 * 登录的购物车
 *  往数据库中存储
 *
 * 未登录的购物车
 *  往cookie + redis中存储
 *
 * /cart/insert
 *
 */
@Controller
@RequestMapping("/cart")
public class CartController {


    @Autowired
    private ICartService cartService;

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
    public String insert(
            @CookieValue(value = "cart_token", required = false) String cartToken,
            Integer gid,
            Integer gnumber,
            User user,
            HttpServletResponse response){
        System.out.println("添加购物车：" + gid + " " + gnumber);
        //判断当前用户是否登录？
        cartToken = cartService.insertCart(gid, gnumber, user, cartToken);

        //将返回的token添加到浏览器的cookie中
        if(cartToken != null){
            Cookie cookie = new Cookie("cart_token", cartToken);
            cookie.setMaxAge(60 * 60 * 24 * 365);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return "succ";
    }

    /**
     * 查询所有购物车列表
     * @return
     */
    @IsLogin
    @RequestMapping("/list")
    @ResponseBody
    public List<Shopcart> queryShopCart(
            @CookieValue(value = "cart_token", required = false) String cartToken,
            User user){

        List<Shopcart> shopcarts = cartService.queryShopCart(cartToken, user);

        return shopcarts;
    }

    /**
     * 合并购物车
     * @return
     */
    @IsLogin
    @RequestMapping("/merge")
    public String mergeShopCart(
            String returnUrl,
            @CookieValue(value = "cart_token", required = false) String cartToken,
            User user, HttpServletResponse response){

        int result = cartService.mergeShopCart(cartToken, user);
        if(result > 0){
            //合并完成,删除临时购物车的cookie
            Cookie cookie = new Cookie("cart_token", null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return "redirect:" + returnUrl;
    }
}
