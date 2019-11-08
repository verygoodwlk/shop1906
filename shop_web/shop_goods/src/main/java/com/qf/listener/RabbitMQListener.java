package com.qf.listener;

import com.qf.service.IGoodsService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RabbitMQListener {

    @Autowired
    private IGoodsService goodsService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "miaosha_goods_queue", durable = "true", declare = "true"),
                    exchange = @Exchange(name = "miaosha_exchange", type = "fanout", durable = "true", declare = "true")
            )
    )
    public void msgHandler(Map<String, Object> map, Message message, Channel channel){

        //做一个幂等设计，防止信息的重复消费
        Integer gid = (Integer) map.get("gid");

        //根据消息修改商品库存
        int flag = goodsService.jianSave(gid);

        if(flag > 0){
            //手动确认消息
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
