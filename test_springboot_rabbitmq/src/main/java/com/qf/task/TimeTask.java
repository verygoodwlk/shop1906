package com.qf.task;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeTask {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerContainerFactory rabbitListenerContainerFactory;


    @Scheduled(initialDelay = 10000, fixedDelay = 1000000)
    public void taskHandler(){
        System.out.println("执行了定时任务！");


//        MessageListenerContainer msgRabbitmq = rabbitListenerEndpointRegistry.getListenerContainer("msgRabbitmq");
//
//        System.out.println("注销监听容器！！" + msgRabbitmq);
////        rabbitListenerEndpointRegistry.unregisterListenerContainer("msgRabbitmq");
//        msgRabbitmq.stop();


        SimpleRabbitListenerEndpoint messageListenerContainer = new SimpleRabbitListenerEndpoint();
        messageListenerContainer.setQueueNames("test_queues");
        messageListenerContainer.setId("testqueueusid");
        messageListenerContainer.setMessageListener(new MessageListenerAdapter(){
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                System.out.println("接收到消息xxxxx：" + new String(message.getBody(), "utf-8"));

                //确认消息
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }
        });

        rabbitListenerEndpointRegistry.registerListenerContainer(messageListenerContainer, rabbitListenerContainerFactory);

        MessageListenerContainer msgRabbitmq = rabbitListenerEndpointRegistry.getListenerContainer("testqueueusid");
        msgRabbitmq.start();


//        if(!msgRabbitmq.isRunning()){
//            System.out.println("开启监听器!");
//            msgRabbitmq.start();
//        }


//        SimpleRabbitListenerEndpoint simpleRabbitListenerEndpoint = new SimpleRabbitListenerEndpoint();
//        simpleRabbitListenerEndpoint.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//
//            }
//        });
//        simpleRabbitListenerEndpoint.setQueueNames("vc");
//
//        simpleRabbitListenerEndpoint.setMessageConverter(new SimpleMessageConverter(){
//
//        });
//
//        rabbitListenerEndpointRegistry.registerListenerContainer(simpleRabbitListenerEndpoint, null);
    }
}
