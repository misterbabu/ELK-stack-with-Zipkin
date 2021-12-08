package com.student.ps.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ElkPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElkPaymentServiceApplication.class, args);
	}

}
