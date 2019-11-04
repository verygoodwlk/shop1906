package com.qf.test_springboot_redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

import java.util.Collections;

@SpringBootTest
class TestSpringbootRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
//        redisTemplate.opsForValue().set("name", "小明");
//        stringRedisTemplate.opsForValue().set("money", "20000");


        //操作String数据结构
        redisTemplate.opsForValue();
        //操作Hash数据结构
        redisTemplate.opsForHash();
        //操作List数据结构
        redisTemplate.opsForList();
        //操作Set数据结构
        redisTemplate.opsForSet();
        //操作ZSet数据结构
        redisTemplate.opsForZSet();

    }

    @Test
    void luaScript(){

        //准备参数
//        List<String> list = new ArrayList<>();
//        list.add("name");

        //执行lua脚本
        DefaultRedisScript defaultRedisScript = new DefaultRedisScript("return redis.call('get', KEYS[1])", String.class);
        Object result = redisTemplate.execute(defaultRedisScript, Collections.singletonList("name"));
        System.out.println("执行lua脚本：" + result);


        //通过获取原始连接的方式操作lua脚本
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.eval();
//        connection.evalSha();
//        connection.scriptLoad();

    }

}
