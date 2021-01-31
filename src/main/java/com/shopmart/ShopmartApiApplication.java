package com.shopmart;

import org.hibernate.SessionFactory;
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

import com.shopmart.model.User;
import com.shopmart.repository.UserRepository;

@SpringBootApplication
public class ShopmartApiApplication implements CommandLineRunner  {

	@Autowired private JdbcTemplate jdbc;
	@Autowired private UserRepository userRepository;
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
//		List<String> products = jdbc.queryForList("select product_id from products", String.class);
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
