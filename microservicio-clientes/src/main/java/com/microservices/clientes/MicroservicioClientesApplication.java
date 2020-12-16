package com.microservices.clientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroservicioClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicioClientesApplication.class, args);
	}

}
