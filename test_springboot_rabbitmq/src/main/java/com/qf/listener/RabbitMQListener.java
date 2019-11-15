package com.qf.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
public class RabbitMQListener {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    private int count;

//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue(name = "test_queue", autoDelete = "true", declare = "true"),
//            exchange = @Exchange(name = "test_exchange", type = "fanout")
//    ), autoStartup = "true", id = "msgRabbitmq")
    public void msgHandler(byte[] bytes, Message message, Channel channel){

        try {
            System.out.println("接收到消息：" + new String(bytes, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }

        count++;

//        if(count % 5 == 0){
//            System.out.println("停止了队列!");
//            rabbitListenerEndpointRegistry.getListenerContainer("msgRabbitmq").stop();
//        }

    }
}
