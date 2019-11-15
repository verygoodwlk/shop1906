package com.qf.mq3;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * 广播模型
 */
public class Provdier {

    public static void main(String[] args) throws IOException {

        //1、
        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //2、通过Channel创建交换机
        //fanout - 广播交换机
        //direct - 路由键交换机
        //topic - 通配符交换机
        channel.exchangeDeclare("test_exchange", "fanout", true);

        //3、发送消息到交换机
        for (int i = 0; i < 10; i++) {
            String info = "Hello msg:" + i;
            channel.basicPublish("test_exchange", "", null, info.getBytes("utf-8"));

        }

        connection.close();
        System.out.println("消息发送完成！");
    }
}
