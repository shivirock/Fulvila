package com.prestaging.fulvila;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FulvilaController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FulvilaController.class);

	@GetMapping("/test")
    public ResponseEntity<String> welcome() {
		System.out.println(" deployment test.."+System.getProperty("java.class.path"));
		LOGGER.info("Test API is working");
		HttpHeaders headers = new HttpHeaders();
		headers.add("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<>("Custom header set", headers, HttpStatus.OK);
    }
}
