package com.qf.handler;

import com.alibaba.fastjson.JSONObject;
import com.qf.util.ChannelUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

/**
 * 自定义消息处理器
 */
@Component
@ChannelHandler.Sharable
public class WsHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端连接了服务器！" + ctx.channel().remoteAddress().toString());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.err.println("有一个客户端断开了连接！" + ctx.channel().remoteAddress().toString());
        //移除映射关系
        ChannelUtil.removeChannel(ctx.channel());
    }

    /**
     * {}
     * []
     * @param text
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame text) throws Exception {
        System.out.println("接收到客户端的消息：" + text.text());

        //将用户的消息转换成JSONObject
        JSONObject jsonObject = JSONObject.parseObject(text.text());

        //判断当前的消息类型
        if(jsonObject.getInteger("msgType") == 1){
            //说明当前是初始化连接的消息
            Integer uid = jsonObject.getInteger("data");
            Channel channel = ctx.channel();

            //将用户关系保存到Map集合中
            ChannelUtil.add(uid, channel);
            return;
        }

        //将消息透传给下一个ChannelHandler处理
        ctx.fireChannelRead(jsonObject);
    }
}
