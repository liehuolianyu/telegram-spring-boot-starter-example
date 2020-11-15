package com.github.xabgesagtx.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ExampleBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExampleBotApplication.class, args);
	}

}
