package com.prestaging.fulvila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Class which holds main method for code entrance
 *
 * @author Shivam Srivastava
 *
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.prestaging.fulvila")
public class FulvilaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FulvilaApplication.class, args);
	}
}
