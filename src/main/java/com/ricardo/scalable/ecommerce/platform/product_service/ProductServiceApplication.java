package com.ricardo.scalable.ecommerce.platform.product_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({ "com.ricardo.scalable.ecommerce.platform.libs_common.entities", "com.ricardo.scalable.ecommerce.platform.product_service.entities" })
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

}
