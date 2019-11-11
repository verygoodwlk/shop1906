package com.qf.listener;

import com.qf.entity.Orders;
import com.qf.service.IOrderService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "miaosha_orders_queue", durable = "true", declare = "true"),
                    exchange = @Exchange(name = "miaosha_exchange", type = "fanout", durable = "true", declare = "true")
            )
    )
    public void msgHandler(Map<String, Object> map, Message message, Channel channel){

        //做一个幂等设计，防止信息的重复消费
        Integer gid = (Integer) map.get("gid");
        Integer uid = (Integer) map.get("uid");
        String orderid = (String) map.get("orderid");

        //验证当前订单是否已经存在

        //生成订单
        Orders orders = orderService.insertMiaoshaOrder(uid, gid, orderid);

        //删除队列中这个订单的消息
        redisTemplate.opsForZSet().remove("miaosha_queue_" + gid, orderid);

        //将订单的信息放入延迟队列中，30分钟后再处理（判断是否已经支付，如果未支付就关闭订单）
        rabbitTemplate.convertAndSend("pay_exchange", "", orderid);

        if(orders != null){
            //手动确认消息
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理死信队列中的消息
     */
    @RabbitListener(queues = "dead_queue")
    public void deadMsgHandler(String orderid, Message message, Channel channel){
        System.out.println("接收到死信队列中的消息：" + orderid);
        //判断订单是否支付
        Orders orders = orderService.queryByOrderId(orderid);

        //判断状态
        if(orders.getStatus() == 0){
            //未支付，关闭订单

            //通过订单号去关闭交易

            //--> 恢复商品的库存

            //修改订单状态到已关闭
            orderService.updateOrderState(orderid, 5);
        }

        //手动确认消息
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
