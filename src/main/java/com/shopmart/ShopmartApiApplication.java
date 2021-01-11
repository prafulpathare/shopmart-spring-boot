package com.shopmart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.shopmart.repository.OrderRepository;

@SpringBootApplication
public class ShopmartApiApplication implements CommandLineRunner  {
	@Autowired private OrderRepository orderRepository;
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
	@Bean
	public MongoDatabase getMmongoDB() {
		MongoClient mongo = new MongoClient(new MongoClientURI("mongodb://localhost:27017/shopmart?readPreference=primary&appname=MongoDB%20Compass&ssl=false"));
		return mongo.getDatabase("shopmart");
	}
	
	@Override
    public void run(String[] args) throws Exception {

		log.info("Application Started");
				
    }
}