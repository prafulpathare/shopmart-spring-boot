package com.shopmart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ShopmartApiApplication implements CommandLineRunner  {
	
	private static final Logger log = LoggerFactory.getLogger(ShopmartApiApplication.class);
	
	public static void main(String[] args) {
		log.info("Starting Application");
		SpringApplication.run(ShopmartApiApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {		
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("*").allowedOrigins("*");
			}
		};
	}
	
		
	@Override
    public void run(String[] args) throws Exception {

		log.info("Application Started");
	}
}
