package com.shopmart;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

@SpringBootApplication
public class ShopmartApiApplication implements CommandLineRunner  {

	@Autowired private JdbcTemplate jdbc;
	@Autowired private com.shopmart.config.MongoConfig mongoConfig;
	
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
//		String[] products = {"5f99305bdf0a8e44f2909dab","5f9932addf0a8e44f2909dac","5f9932addf0a8e44f2909dad","5f9932addf0a8e44f2909dae","5f9932addf0a8e44f2909daf","5f9932addf0a8e44f2909db0","5f9933f0df0a8e44f2909db1","5f9933f0df0a8e44f2909db2","5f9933f0df0a8e44f2909db3","5f9933f0df0a8e44f2909db4", "5f9b2a418984f521f37f622b"};
//		
//		double pin;
//		
//		for(int i = 0; i <= 200; i++) {			
//			for (String productId : products) {
//				jdbc.update("insert into shopmart.product_analytics (product_id, views, orders, date) values (?, ?, ?, ?)",
//					productId,
//					Math.random() * ( 550 - 75 ),
//					Math.random() * ( 200 - 30 ),
//					new Date(System.currentTimeMillis() - (i * 24*60*60*1000))
//				);
//			}
//		}
		
		
		
		
	}
}
