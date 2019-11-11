package com.qf.shop_order;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    /**
     * 普通队列 - 30分钟的消息过期时间
     * @return
            */
    @Bean("payQueue")
    public Queue getNormalQueue(){
        Map<String, Object> map = new HashMap<>();
        map.put("x-message-ttl", 1000 * 60 * 2);
        map.put("x-dead-letter-exchange", "dead_exchange");
        return new Queue("pay_queue", true, false, false, map);
    }

    @Bean("payExchange")
    public FanoutExchange getNormalExchange(){
        return new FanoutExchange("pay_exchange", true, false);
    }

    /**
     * 绑定
     * @param payQueue
     * @param payExchange
     * @return
     */
    @Bean
    public Binding getNormalBinding(Queue payQueue, FanoutExchange payExchange){
        return BindingBuilder.bind(payQueue).to(payExchange);
    }

    /**
     * 死信队列
     * @return
     */
    @Bean("deadQueue")
    public Queue getDeadQueue(){
        return new Queue("dead_queue", true);
    }

    @Bean("deadExchange")
    public FanoutExchange getDeadExchange(){
        return new FanoutExchange("dead_exchange", true, false);
    }

    /**
     * 绑定死信队列和交换机
     * @return
     */
    @Bean
    public Binding getDeadBinding(Queue deadQueue, FanoutExchange deadExchange){
        return BindingBuilder.bind(deadQueue).to(deadExchange);
    }

}
