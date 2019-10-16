package com.qf.mq4;

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
        channel.exchangeDeclare("myexchange", "direct");//路邮键

        //3、发送消息到交换机
        String info = "Hello Direct - update";
        channel.basicPublish("myexchange", "update", null, info.getBytes("utf-8"));

        connection.close();
        System.out.println("消息发送完成！");
    }
}
