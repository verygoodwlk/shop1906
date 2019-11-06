package com.qf.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.qf.aop.IsLogin;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    private GoodsFeign goodsFeign;
    
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private DefaultKaptcha defaultKaptcha;


    /**
     * 获取当前的秒杀商品信息
     * [{
     *     "startTime":"14:00:00",
     *     "endTime":"15:00:00",
     *     "goods":[{"subject":"", "fengmian":"", "goodsMiaosha":{}},{},{}]
     * },{
     *     "startTime":"15:00:00",
     *     "endTime":"16:00:00",
     *     "goods":[{"":"",},{}]
     * }]
     *
     *
     * @return
     */
    @RequestMapping("/queryByTime")
    @ResponseBody
    public List<Map<String, Object>> queryNowMiaosha(){
        List<Map<String, Object>> maps = goodsFeign.queryMiaoshaByTime();
        return maps;
    }

    /**
     * 获取验证码
     * swing awt - C/S B/S
     */
    @RequestMapping("/getCode")
    @ResponseBody
    public void getCode(HttpServletResponse response){

        //生成验证码 - 保存到服务器
        String text = defaultKaptcha.createText();

        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(token, text);
        redisTemplate.expire(token, 5, TimeUnit.MINUTES);

        //根据验证码生成图片
        BufferedImage image = defaultKaptcha.createImage(text);

        //回写cookie
        Cookie cookie = new Cookie("code_token", token);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 5);
        response.addCookie(cookie);

        //将验证码图片写回客户端
        try {
            ImageIO.write(image, "jpg", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得服务器的当前时间
     * @return
     */
    @ResponseBody
    @RequestMapping("/getNow")
    public Date getNow(){
        return new Date();
    }

    /**
     * 秒杀抢购
     *
     * local gid = ARGV[1]
     * local profix = redis.call('get', 'time_profix')
     * local flag = redis.call('sismember', 'miaosha_start_'..profix, gid)
     * return flag
     *
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/qiangGou")
    public String qiangGou(Integer gid, User user){
        System.out.println(user.getNickname() + "抢购了id为" + gid + "的商品！");
        return "succ";
    }
}
