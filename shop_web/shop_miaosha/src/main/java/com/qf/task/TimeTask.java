package com.qf.task;

import com.qf.util.ContactUtil;
import com.qf.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

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

        //将当前时间转换成评分
        double score = TimeUtil.date2Score(new Date());
        System.out.println("当前评分：" + score);

        //优化的手段：1、lua脚本  2、redis流水线

        //流水线
        redisTemplate.executePipelined(new SessionCallback<String>() {
            @Override
            public String execute(RedisOperations operations) throws DataAccessException {

                //获得当前时间，从redis中获得相同评分的商品id
                Set<String> gidStrs = operations.opsForZSet().rangeByScore(
                        ContactUtil.REDIS_MIAOSHA_SORT_SET, score, score);

                System.out.println("查询结果：" + gidStrs);

                //删除有序集合
//                operations.opsForZSet().removeRangeByScore(
//                        ContactUtil.REDIS_MIAOSHA_SORT_SET, score, score
//                );

                //当前整点有秒杀商品
                if(gidStrs != null && gidStrs.size() > 0){
                    for (String gid : gidStrs) {
                        System.out.println("保存到集合中：" + gid);
                        operations.opsForSet().add(
                                ContactUtil.REDIS_MIAOSHA_START_SET, gid);
                    }
                }

                return null;
            }
        }, new StringRedisSerializer());


    }
}
