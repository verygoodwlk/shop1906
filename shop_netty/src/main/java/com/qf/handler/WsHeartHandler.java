package com.qf.handler;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

@Component
@ChannelHandler.Sharable
public class WsHeartHandler extends SimpleChannelInboundHandler<JSONObject> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JSONObject jsonObject) throws Exception {
        if(jsonObject.getInteger("msgType") == 2){
            System.out.println("接收到心跳消息");
            //回复心跳消息
            ctx.writeAndFlush(new TextWebSocketFrame(jsonObject.toJSONString()));
            return;
        }

        //继续透传
        ctx.fireChannelRead(jsonObject);
    }
}
