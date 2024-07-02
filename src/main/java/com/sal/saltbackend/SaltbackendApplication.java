package com.sal.saltbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaltbackendApplication {

	public static void main(String[] args) {
		System.setProperty("server.address", "0.0.0.0");
//		172.20.10.7
		SpringApplication.run(SaltbackendApplication.class, args);

	}

}