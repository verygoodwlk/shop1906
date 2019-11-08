package com.qf.listener;

import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class RabbitMQListener {

    @Autowired
    private IOrderService orderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "miaosha_orders_queue", durable = "true", declare = "true"),
                    exchange = @Exchange(name = "miaosha_exchange", type = "fanout", durable = "true", declare = "true")
            )
    )
    public void msgHandler(Map<String, Object> map, Message message, Channel channel){

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //做一个幂等设计，防止信息的重复消费
        Integer gid = (Integer) map.get("gid");
        Integer uid = (Integer) map.get("uid");
        String orderid = (String) map.get("orderid");

        //验证当前订单是否已经存在

        //生成订单
        Orders orders = orderService.insertMiaoshaOrder(uid, gid, orderid);

        //删除队列中这个订单的消息
        redisTemplate.opsForZSet().remove("miaosha_queue_" + gid, orderid);

        if(orders != null){
            //手动确认消息
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
