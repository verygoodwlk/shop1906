package com.qf.mq1;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息提供者
 */
public class Provider {

    public static void main(String[] args) throws IOException, TimeoutException {
        //1--------------------连接RabbitMQ--------------------------
        Connection connection = ConnectionUtil.getConnection();

        //2--------------------通过连接获得RabbitMQ的Channel对象-------------------
        //后续rabbitmq的所有操作都是基于Channel进行的
        Channel channel = connection.createChannel();

        //3--------------------通过channel创建队列------------------------
        channel.queueDeclare("myqueue", false, false, false, null);

        //4--------------------发送消息到队列中----------------------------
        for (int i = 1; i <= 10; i++) {
            String info = "Hello RabbitMQ!" + i;
            channel.basicPublish("", "myqueue", null, info.getBytes("utf-8"));
        }


        //5--------------------关闭连接----------------------
        connection.close();
        System.out.println("消息发送完成");
    }
}
