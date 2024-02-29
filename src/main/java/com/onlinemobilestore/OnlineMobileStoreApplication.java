package com.onlinemobilestore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class OnlineMobileStoreApplication {
	public static void main(String[] args) {
		SpringApplication.run(OnlineMobileStoreApplication.class, args);
	}
}
