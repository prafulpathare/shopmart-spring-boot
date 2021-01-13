package com.shopmart;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

@SpringBootApplication
public class ShopmartApiApplication implements CommandLineRunner  {

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
		FindIterable<Document> products = mongoConfig.productCollection().find();
		for (Document document : products) {
			 mongoConfig.productCollection().updateOne(Filters.eq("_id", new ObjectId(document.getObjectId("_id").toString())), Updates.setOnInsert("is_approved", false));
			 mongoConfig.productCollection().updateOne(Filters.eq("_id", new ObjectId(document.getObjectId("_id").toString())), Updates.setOnInsert("views", 0));
		}
	}
}
