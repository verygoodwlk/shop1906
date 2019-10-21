package com.qf.mq5;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Provider {

    public static void main(String[] args) throws IOException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明优先级队列
        Map<String, Object> map = new HashMap<>();
        map.put("x-max-priority", 200);
        map.put("x-dead-letter-exchange", "dead_exchange");


        //声明死信队列
        channel.exchangeDeclare("dead_exchange", "fanout");
        channel.queueDeclare("dead_queue", false, false, false, null);
        channel.queueBind("dead_queue", "dead_exchange", "");

        //声明普通队列和普通交换机
        channel.exchangeDeclare("myexchange", "fanout");
        channel.queueDeclare("myqueue", false, false, false, map);
        channel.queueBind("myqueue", "myexchange", "");


        //给队列发送消息
        for (int i = 0; i < 10; i++) {

            //设置优先级
            int pro = (int) (Math.random() * 20);
            if(i == 0){
                pro = 0;
            }

            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();
            AMQP.BasicProperties.Builder builder = basicProperties.builder();
            builder.priority(pro);

            //设置过期时间
            String expire = (20 - pro) * 1000 + "";
            builder.expiration(expire);



            String info = "消息内容" + i + ", 消息权重：" + pro + ", 过期时间：" + (Integer.parseInt(expire)/1000) + "秒";
            channel.basicPublish("myexchange", "", builder.build(), info.getBytes("UTF-8"));
            System.out.println(new SimpleDateFormat("hh:mm:ss").format(new Date()) + " -> 发送消息：" + info);
        }

//        channel.queuePurge("myqueue");
        System.out.println("消息发送完成！");
    }
}
