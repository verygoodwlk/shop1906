package com.qf.mq1;

import com.qf.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 消费者
 */
public class Consumer2 {

    public static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        //1--------------------连接RabbitMQ--------------------------
        Connection connection = ConnectionUtil.getConnection();

        //2--------------------通过连接获得Channel--------------------
        Channel channel = connection.createChannel();

        //3--------------------通过channel创建队列------------------------
        channel.queueDeclare("myqueue", false, false, false, null);

        //3--------------------监听队列是否有消息----------------------
        channel.basicConsume("myqueue", true, new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                //将消费消息的代码放入线程池中执行
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            System.out.println("消费者2接收到队列中的消息：" + new String(body, "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
