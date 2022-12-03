package com.wfh.shopping.item;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.wfh.shopping")
public class ItemApp {
    public static void main(String[] args) {
        SpringApplication.run(ItemApp.class,args);
    }
}
