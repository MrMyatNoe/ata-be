package com.tmn.ata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.cloudyrock.spring.v5.EnableMongock;

@SpringBootApplication(scanBasePackages = "com.tmn.ata")
@EnableMongoRepositories
@EnableMongock
@EnableConfigurationProperties
public class AtaBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtaBeApplication.class, args);
	}

}
