package com.example.newsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching // This annotation enables the caching mechanism
public class NewsapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsapiApplication.class, args);
	}

	// Define the RestTemplate bean
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
