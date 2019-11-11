package com.qf.task;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeTask {

    @Autowired
    private RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Scheduled(initialDelay = 10000, fixedDelay = 1000000)
    public void taskHandler(){
        System.out.println("执行了定时任务！");


        MessageListenerContainer msgRabbitmq = rabbitListenerEndpointRegistry.getListenerContainer("msgRabbitmq");


        if(!msgRabbitmq.isRunning()){
            System.out.println("开启监听器!");
            msgRabbitmq.start();
        }


        SimpleRabbitListenerEndpoint simpleRabbitListenerEndpoint = new SimpleRabbitListenerEndpoint();
        simpleRabbitListenerEndpoint.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {

            }
        });
        simpleRabbitListenerEndpoint.setQueueNames("vc");

        simpleRabbitListenerEndpoint.setMessageConverter(new SimpleMessageConverter(){

        });

        rabbitListenerEndpointRegistry.registerListenerContainer(simpleRabbitListenerEndpoint, null);
    }
}
