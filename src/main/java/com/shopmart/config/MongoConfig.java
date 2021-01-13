package com.shopmart.config;

import org.bson.Document;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Configuration
public class MongoConfig {

	@Bean
	public MongoDatabase mongodb() {
		 return new MongoClient(
			new MongoClientURI(
				"mongodb://localhost:27017/shopmart?readPreference=primary&appname=MongoDB%20Compass&ssl=false"
			)
		).getDatabase("shopmart");
	}
	
	@Bean public MongoCollection<Document> productCollection() {
		return this.mongodb().getCollection("products");
	}
	
}
