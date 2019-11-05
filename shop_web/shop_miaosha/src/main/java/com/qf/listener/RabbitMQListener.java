package com.qf.listener;

import com.qf.entity.Goods;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

@Component
public class RabbitMQListener {

    @Autowired
    private Configuration configuration;

    @RabbitListener(
            bindings = @QueueBinding(
                    exchange = @Exchange(name = "goods_exchange", type = "direct", durable = "true"),
                    value = @Queue("miaosha_queue"),
                    key = "miaosha"))
    public void msgHandler(Goods goods){
        System.out.println("秒杀服务收到生成秒杀静态页的消息：" + goods);

        //准备一个输入路径
        //获得classpath路径
        String path = RabbitMQListener.class.getResource("/static/miaosha").getPath();

        //通过freemarker生成静态页面
        try(
                Writer writer = new FileWriter(path + "/" + goods.getId() + ".html");
        ) {
            Template template = configuration.getTemplate("miaosha.ftl");

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
