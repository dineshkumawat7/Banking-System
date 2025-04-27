package com.bank.common.lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CommonLibApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonLibApplication.class, args);
    }

}
