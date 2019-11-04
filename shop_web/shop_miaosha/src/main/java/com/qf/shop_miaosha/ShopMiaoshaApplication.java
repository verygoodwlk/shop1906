package com.qf.shop_miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.qf.feign")
public class ShopMiaoshaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopMiaoshaApplication.class, args);
    }

}
