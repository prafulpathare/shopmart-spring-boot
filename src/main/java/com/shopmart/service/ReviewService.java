package com.shopmart.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.MongoDatabase;
import com.shopmart.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired private ReviewRepository reviewRepo;
	@Autowired private JdbcTemplate jdbc;
	@Autowired private MongoDatabase mongodb;
	@Autowired private UserService userServ;
	private static Logger log = LoggerFactory.getLogger(ReviewService.class);
	
}