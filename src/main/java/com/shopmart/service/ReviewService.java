package com.shopmart.service;

import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.shopmart.model.Review;
import com.shopmart.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired private ReviewRepository reviewRepository;
	@Autowired private JdbcTemplate jdbc;
	@Autowired private UserService userService;
	private static Logger log = LoggerFactory.getLogger(ReviewService.class);

	public void create(Review review){
		reviewRepository.save(
			new Review(
				review.getProduct_id(), review.getReply_to(),
				review.getReview_txt(), new Date(),
				userService.get()
			)
		);
	}
	public Set<Review> get(String productId){
		return reviewRepository.findByProductId(productId);
	}
	public void delete(String productId){
		jdbc.update("delete from shopmart.reviews where product_id = ?", new Object[]{productId});
	}
}
