package com.qf.mq5;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 消费者
 */
public class Consumer {


    public static void main(String[] args) throws IOException {
        //1--------------------连接RabbitMQ--------------------------
        Connection connection = ConnectionUtil.getConnection();

        //2--------------------通过连接获得Channel--------------------
        Channel channel = connection.createChannel();

        //3--------------------监听队列是否有消息----------------------
        channel.basicConsume("myqueue", true, new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("获得优先级队列中的消息：" + new String(body, "utf-8"));
            }
        });
    }
}
