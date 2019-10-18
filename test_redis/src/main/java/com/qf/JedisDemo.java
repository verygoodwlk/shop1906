package com.qf;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisDemo {

    public static void main(String[] args) {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMinIdle(10);
        config.setMaxIdle(200);

        JedisPool jedisPool = new JedisPool(config, "192.168.195.129");

        //1、连接redis
        Jedis jedis = jedisPool.getResource();

        //2、通过jedis对象操作redis
        jedis.set("money", "10000");
        String money = jedis.get("money");
        System.out.println("redis中的取值：" + money);

        //3、关闭连接
        jedis.close();
    }
}
