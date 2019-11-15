package com.qf.shop_netty;

import com.qf.handler.WsHandler;
import com.qf.handler.WsHeartHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 当前类的run方法会在SpringBoot启动时触发
 */
@Component
public class SpringBootInit implements CommandLineRunner {

    private EventLoopGroup master = new NioEventLoopGroup();
    private EventLoopGroup slave = new NioEventLoopGroup();

    @Value("${server.port}")
    private int port;

    @Autowired
    private WsHandler wsHandler;
    @Autowired
    private WsHeartHandler wsHeartHandler;

    @Override
    public void run(String... args) throws Exception {
        //启动Netty服务
        ServerBootstrap bootstrap = new ServerBootstrap();
        //设置线程模型
        bootstrap.group(master, slave)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();

                        //添加对Http的支持
                        pipeline.addLast(new HttpServerCodec());
                        pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
                        //添加对WebSocket的支持
                        pipeline.addLast(new WebSocketServerProtocolHandler("/"));

                        //添加心跳超时的处理器
                        pipeline.addLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS));

                        //添加自定义的消息处理器
                        pipeline.addLast(wsHandler);
                        pipeline.addLast(wsHeartHandler);


                    }
                });

        //绑定端口
        bootstrap.bind(port).sync();
        System.out.println("Netty服务已经启动，端口为：" + port);
    }
}
