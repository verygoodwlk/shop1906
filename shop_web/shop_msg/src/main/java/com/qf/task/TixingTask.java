package com.qf.task;

import com.alibaba.fastjson.JSONObject;
import com.qf.util.TimeUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

@Component
public class TixingTask {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 任意一个小时50分时都会触发这个方法
     */
    @Scheduled(cron = "0 50 * * * *")
    public void tixingTime(){

        //计算当前时间的评分
        String score = TimeUtil.date2Score(new Date());
        //根据评分找出redis中需要提醒的消息信息
        Set<String> miaoshaTixing = redisTemplate.opsForZSet().rangeByScore("miaosha_tixing", Double.valueOf(score), Double.valueOf(score));

        //删除提醒消息
        redisTemplate.opsForZSet().removeRangeByScore("miaosha_tixing", Double.valueOf(score), Double.valueOf(score));

        //处理提醒消息
        if(miaoshaTixing != null && miaoshaTixing.size() > 0){
            for (String tixingJson : miaoshaTixing) {
                //提醒消息
                JSONObject jsonObject = JSONObject.parseObject(tixingJson);

                //将这个提醒的消息封装成一个消息对象 -> rabbitmq -> netty -> websocket -> 客户端
                jsonObject.put("msgType", 3);//{"gid":xxx, "uid":xxx, "msgType":3}

                //发送到rabbitmq中
                rabbitTemplate.convertAndSend("netty_exchange", "", jsonObject.toString());
            }
        }
    }
}
