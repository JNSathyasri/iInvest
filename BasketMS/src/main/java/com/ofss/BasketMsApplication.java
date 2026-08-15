package com.ofss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@EnableDiscoveryClient
@SpringBootApplication
public class BasketMsApplication 
{
	public static void main(String[] args) 
	{
		SpringApplication.run(BasketMsApplication.class, args);
	}
	@Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
  	  System.out.print("Returning an object"); return new RestTemplate(); }
}
