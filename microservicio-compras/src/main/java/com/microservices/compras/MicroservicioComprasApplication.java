package com.microservices.compras;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class MicroservicioComprasApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioComprasApplication.class, args);
	}

}
