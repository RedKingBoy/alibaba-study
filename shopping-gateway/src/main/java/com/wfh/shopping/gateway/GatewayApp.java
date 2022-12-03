package com.wfh.shopping.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wfh.shopping")
public class GatewayApp{
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class,args);
    }
}
