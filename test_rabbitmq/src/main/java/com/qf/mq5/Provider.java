package com.qf.mq5;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Provider {

    public static void main(String[] args) throws IOException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //声明优先级队列
        Map<String, Object> map = new HashMap<>();
        map.put("x-max-priority", 200);

        channel.queueDeclare("myqueue", false, false, false, map);

        //声明交换机
        channel.exchangeDeclare("myexchange", "fanout");

        //绑定
        channel.queueBind("myqueue", "myexchange", "");

        //声明死信队列


        //给队列发送消息
        for (int i = 0; i < 10; i++) {

            //设置优先级
            int pro = (int) (Math.random() * 200);
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties();
            AMQP.BasicProperties.Builder builder = basicProperties.builder();
            builder.priority(pro);

            //设置过期时间
            builder.expiration(pro + "");


            String info = "消息内容" + i + ", 消息权重：" + pro;
            channel.basicPublish("myexchange", "", builder.build(), info.getBytes("UTF-8"));
        }

        System.out.println("消息发送完成！");
    }
}
