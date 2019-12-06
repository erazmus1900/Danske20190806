package com.erazmus1900;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.erazmus1900.property.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({ FileStorageProperties.class })
public class SpringBootCrudRestfulApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudRestfulApplication.class, args);

	}

}
