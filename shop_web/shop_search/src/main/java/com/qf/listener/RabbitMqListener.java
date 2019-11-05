package com.qf.listener;

import com.qf.entity.Goods;
import com.qf.service.ISearchService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ消息的监听类
 */
@Component
public class RabbitMqListener {

    @Autowired
    private ISearchService searchService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search_queue", durable = "true"),
            exchange = @Exchange(name = "goods_exchange", durable = "true", type = "direct"),
            key = {"normal", "miaosha"}))
    public void goodsMsgHandler(Goods goods){
        System.out.println("接收到RabbitMQ的消息！，并且同步到索引库中");
        //同步索引库
        searchService.insert(goods);
    }
}
