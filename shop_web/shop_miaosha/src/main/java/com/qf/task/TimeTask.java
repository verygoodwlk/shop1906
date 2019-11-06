package com.qf.task;

import com.qf.util.ContactUtil;
import com.qf.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TimeTask {
    
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    /**
     * 整点定时任务 - 秒杀开始
     *
     * cron表达式（秒 分 时 天 月 [星期] 年）
     */
    @Scheduled(cron = "0 0 0/1 * * *")
    public void miaoshaStart(){
        System.out.println("午时已到！！");

        //更新redis中的整点时间即可
        String profix = TimeUtil.date2Score(new Date());
        //将当前时间字符串，更新到redis中
        redisTemplate.opsForValue().set(ContactUtil.REDIS_MIAOSHA_TIME_PROFIX, profix);

        //删除上一个时间点的商品集合（节省redis空间）
        Date preX = TimeUtil.getNextX(-1);
        String profix2 = TimeUtil.date2Score(preX);
        redisTemplate.delete(ContactUtil.REDIS_MIAOSHA_START_SET + "_" + profix2);

        //删除缓存
        redisTemplate.delete("miaosha::indexList");
    }
}
