package com.qf.shop_item;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopItemApplicationTests {

    /**
     * 注入freemarker的配置对象
     */
    @Autowired
    private Configuration configuration;

    @Test
    public void contextLoads() throws Exception {

        //获得模板对象
        Template test = configuration.getTemplate("test.ftl");

        //准备数据集合
        Map map = new HashMap();
        map.put("key", "小明");
        map.put("age", 118);
        map.put("likes", new String[]{"唱歌","跳舞","rap","篮球"});
        map.put("now", new Date());
        map.put("money", 9992.976);

        Writer out = new FileWriter("C:\\Users\\Ken\\Desktop\\test.html");

        //将数据和模板整合生成一个静态页面
        test.process(map, out);
        out.close();

    }

}
