package com.qf.listener;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * RabbitMQ消息的监听类
 */
@Component
public class RabbitMqListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(queues = "item_queue")
    public void goodsMsgHandler(Goods goods){
        System.out.println("详情服务接收到RabbitMQ的消息！，生成静态页");

        //准备一个输入路径
        //获得classpath路径
        String path = RabbitMqListener.class.getResource("/static/html").getPath();

        //通过freemarker生成静态页面
        try(
                Writer writer = new FileWriter(path + "/" + goods.getId() + ".html");
        ) {
            Template template = configuration.getTemplate("goods.ftl");

            //准备数据
            Map<String, Object> map = new HashMap<>();
            map.put("goods", goods);

            //生成静态页面
            template.process(map, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
