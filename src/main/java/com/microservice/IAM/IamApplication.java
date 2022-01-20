package com.microservice.IAM;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class IamApplication {

	public static void main(String[] args) {
		SpringApplication.run(IamApplication.class, args);
	}
}
