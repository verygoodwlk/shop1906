package com.qf.shop_order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.qf.feign")
@MapperScan("com.qf.dao")
@EnableTransactionManagement
public class ShopOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopOrderApplication.class, args);
    }

}
