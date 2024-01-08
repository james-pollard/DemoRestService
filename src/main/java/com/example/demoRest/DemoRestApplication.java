package com.example.demoRest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class DemoRestApplication {


	public static void main(String[] args) {
		SpringApplication.run(DemoRestApplication.class, args);
	}

}
