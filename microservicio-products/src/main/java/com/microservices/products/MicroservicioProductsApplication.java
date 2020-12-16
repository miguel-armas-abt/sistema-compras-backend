package com.microservices.products;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MicroservicioProductsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioProductsApplication.class, args);
	}

}