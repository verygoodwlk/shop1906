package com.qf.controller;

import com.alibaba.fastjson.JSON;
import com.qf.aop.IsLogin;
import com.qf.entity.Goods;
import com.qf.entity.User;
import com.qf.feign.GoodsFeign;
import com.qf.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/msg")
public class MsgController {

    @Autowired
    private GoodsFeign goodsFeign;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 秒杀提醒
     * @return
     */
    @IsLogin(mustLogin = true)
    @RequestMapping("/tixing")
    @ResponseBody
    public String tixing(Integer gid, User user){
        System.out.println(user.getId() + "设置秒杀提醒：" + gid);

        Map<String, Object> map = new HashMap<>();
        map.put("gid", gid);
        map.put("uid", user.getId());

        //获得商品开抢时间
        Goods goods = goodsFeign.queryById(gid);
        Date startTime = goods.getGoodsMiaosha().getStartTime();

        //根据开抢时间计算前10分钟的时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MINUTE, -10);
        Date tixingTime = calendar.getTime();//计算出消息提醒的时间对象

        //计算提醒的消息评分
        String score = TimeUtil.date2Score(tixingTime);

        //将提醒信息放入redis中
        redisTemplate.opsForZSet().add("miaosha_tixing", JSON.toJSONString(map), Double.valueOf(score));

        return "succ";
    }
}
