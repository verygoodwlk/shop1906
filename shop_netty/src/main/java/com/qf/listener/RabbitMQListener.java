package com.qf.listener;

import com.alibaba.fastjson.JSONObject;
import com.qf.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "netty_queue_${server.ip}_${server.port}", durable = "true"),
                    exchange = @Exchange(name = "netty_exchange", type = "fanout")
            )
    )
    public void msgHandler(String msg) {
        System.out.println("netty服务中心收到消息服务发送的提醒数据：" + msg);

        //将字符串转成jsonobject
        JSONObject jsonObject = JSONObject.parseObject(msg);
        int msgType = jsonObject.getInteger("msgType");

        if(msgType == 3){
            //秒杀提醒的消息
            Integer uid = jsonObject.getInteger("uid");

            //通过用户id查询出对应的Channel
            Channel channel = ChannelUtil.getUid(uid);
            if(channel != null){

                //说明用户在线
                channel.writeAndFlush(new TextWebSocketFrame(msg));

            } else {
                System.out.println(uid + "用户未上线！");
            }
        }
    }
}
