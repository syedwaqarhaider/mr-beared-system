package com.beared.queueservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.beared.queueservice.clients")
public class BearedQueueServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BearedQueueServiceApplication.class, args);
	}

}
