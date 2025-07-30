package com.beared.shopservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.beared.shopservice.clients.queue")
public class BearedShopServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BearedShopServiceApplication.class, args);
	}

}
