package com.tmn.ata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tmn.ata")
public class AtaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtaBeApplication.class, args);
	}
}
