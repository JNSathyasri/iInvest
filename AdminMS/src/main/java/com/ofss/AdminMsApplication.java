package com.ofss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class AdminMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminMsApplication.class, args);
		System.out.println("Admin Microservice Started.");
	}
	
//	@Bean
//	@LoadBalanced
//	public RestTemplate restTemplate() {
//		System.out.println("Creating RestTemplate object");
//		return new RestTemplate();
//	}
}
