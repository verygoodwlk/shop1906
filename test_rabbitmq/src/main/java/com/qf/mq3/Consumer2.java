package com.qf.mq3;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Consumer2 {

    public static void main(String[] args) throws IOException {

        Connection connection = ConnectionUtil.getConnection();

        Channel channel = connection.createChannel();

        //创建队列
        channel.queueDeclare("consumer2_queue", false, false, false,null);

        //将队列和交换机绑定
        channel.queueBind("consumer2_queue", "myexchange", "");//队列绑定交换机
//        channel.exchangeBind();//交换机绑定交换机

        //监听队列
        channel.basicConsume("consumer2_queue", true, new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2接收到消息：" + new String(body, "utf-8"));
            }
        });
    }
}
