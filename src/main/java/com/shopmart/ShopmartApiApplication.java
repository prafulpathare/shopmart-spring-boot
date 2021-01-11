package com.shopmart;

import java.math.BigInteger;
import java.util.Random;

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
import com.shopmart.model.Product;
import com.shopmart.model.Supplier;
import com.shopmart.model.Supplier.SupplierType;
import com.shopmart.repository.OrderRepository;
import com.shopmart.repository.ProductRepository;
import com.shopmart.repository.SupplierRepository;

@SpringBootApplication
public class ShopmartApiApplication implements CommandLineRunner  {
	@Autowired private ProductRepository productRepository;
	@Autowired private SupplierRepository repository;
	
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
		
		Supplier s = repository.save(new Supplier("vardahn sahani", "NaN", "sahanivardhan@gmail.com","8741258745", true, 1, SupplierType.BUSSINESS, "7412587896", "753159852456197"));
		productRepository.save(new Product(530665656565885L, s));
				
    }
}