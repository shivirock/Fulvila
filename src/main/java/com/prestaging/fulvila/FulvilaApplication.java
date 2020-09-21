package com.prestaging.fulvila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * Class which holds main method for code entrance
 *
 * @author Shivam Srivastava
 *
 */
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.prestaging.fulvila")
public class FulvilaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FulvilaApplication.class, args);
	}
}
