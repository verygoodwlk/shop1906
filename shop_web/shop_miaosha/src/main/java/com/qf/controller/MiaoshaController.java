package com.qf.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.qf.aop.IsLogin;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import com.qf.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 判断库存的lua脚本
     */
    private String lua = "local gid = KEYS[1]\n" +
            "local orderid = ARGV[1]\n" +
            "local now = ARGV[2]\n" +
            "local save = tonumber(redis.call('get', 'miaosha_save_'..gid))\n" +
            "if save >= 1 then\n" +
            "\tredis.call('decr', 'miaosha_save_'..gid)\t\n" +
            "\tredis.call('zadd', 'miaosha_queue_'..gid, now, orderid)\n" +
            "\treturn 1\n" +
            "end\n" +
            "return 0";


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
    public String qiangGou(Integer gid, User user, Model model){
        System.out.println(user.getNickname() + "抢购了id为" + gid + "的商品！");

        //生成订单号
        String orderid = UUID.randomUUID().toString();
        String score = TimeUtil.date2Socre2(new Date());

        //判断商品库存
        Long flag = redisTemplate.execute(new DefaultRedisScript<>(lua, Long.class),
                Collections.singletonList(gid + ""),
                orderid,
                score);

        if(flag == 0){
            //库存不足
            return "error3";
        }

        //抢购成功，将消息放入mq，保证订单和库存最终一致性，返回用户一个排队页面
        Map<String, Object> map = new HashMap<>();
        map.put("gid", gid);
        map.put("uid", user.getId());
        map.put("orderid", orderid);
        model.addAllAttributes(map);

        rabbitTemplate.convertAndSend("miaosha_exchange", "", map);

        return "paidui";
    }


    /**
     * 查询排队的情况
     * @return
     */
    @RequestMapping("/queryPaidui")
    @ResponseBody
    public Map<String, Object> queryPaidui(String orderid, Integer gid){

        Map<String, Object> map = new HashMap<>();
        Long number = redisTemplate.opsForZSet().rank("miaosha_queue_" + gid, orderid);

        if(number != null){
            map.put("code", 0);//0表示还在队列中
            map.put("number", number + 1);
        } else {
            //通过feign去查询订单服务是否有orderid的订单信息，有的话说明已经抢购成功跳转到订单完善页面
            //返回抢购失败
            map.put("code", 1);//抢购成功
        }

        return map;
    }
}
