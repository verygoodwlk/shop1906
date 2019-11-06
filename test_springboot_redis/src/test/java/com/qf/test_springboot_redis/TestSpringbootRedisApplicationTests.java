package com.qf.test_springboot_redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class TestSpringbootRedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {

        stringRedisTemplate.executePipelined(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                for (int i = 1; i <= 100000; i++) {
                    operations.opsForSet().add("miaosha_start_19110610", i + "");
                }
                return null;
            }
        });
    }

    @Test
    void luaScript(){

        //准备参数
//        List<String> list = new ArrayList<>();
//        list.add("name");

        //执行lua脚本
//        DefaultRedisScript defaultRedisScript = new DefaultRedisScript("return redis.call('get', KEYS[1])", String.class);
//        Object result = redisTemplate.execute(defaultRedisScript, Collections.singletonList("name"));
//        System.out.println("执行lua脚本：" + result);


        //通过获取原始连接的方式操作lua脚本
//        RedisConnection connection = redisTemplate.getConnectionFactory().getConnection();
//        connection.eval();
//        connection.evalSha();
//        connection.scriptLoad();

    }

}
