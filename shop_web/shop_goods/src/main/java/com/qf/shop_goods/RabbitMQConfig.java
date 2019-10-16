package com.qf.shop_goods;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    /*
    创建队列
     */
    @Bean("search_queue")
    public Queue getQueue(){
        return new Queue("search_queue");
    }

    @Bean("item_queue")
    public Queue getQueue2(){
        return new Queue("item_queue");
    }

    /*
    创建交换机
     */
    @Bean("goods_exchange")
    public FanoutExchange getExchange(){
        return new FanoutExchange("goods_exchange");
    }

    /*
    交换机和队列进行绑定
     */
    @Bean
    public Binding getBinding1(Queue search_queue, FanoutExchange goods_exchange){
       return BindingBuilder.bind(search_queue).to(goods_exchange);
    }

    @Bean
    public Binding getBinding2(Queue item_queue, FanoutExchange goods_exchange){
        return BindingBuilder.bind(item_queue).to(goods_exchange);
    }
}
