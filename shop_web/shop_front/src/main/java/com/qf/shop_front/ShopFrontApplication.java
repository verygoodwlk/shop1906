package com.qf.shop_front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ShopFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopFrontApplication.class, args);
    }

}
