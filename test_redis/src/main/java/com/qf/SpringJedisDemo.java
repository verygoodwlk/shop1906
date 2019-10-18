package com.qf;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringJUnitConfig(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext-redis.xml")
public class SpringJedisDemo {

    @Test
    public void test(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-redis.xml");
        RedisTemplate redisTemplate = context.getBean(RedisTemplate.class);

        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
        System.out.println("获得原始连接：" + connection);

        redisTemplate.opsForValue().set("money", 50000);
        System.out.println("数据添加成功！");

//        Object name = redisTemplate.opsForValue().get("name");
//        System.out.println("数据查询成功:" + name);
    }
}
