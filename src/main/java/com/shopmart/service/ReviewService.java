package com.shopmart.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.shopmart.dto.ReviewResponse;
import com.shopmart.model.Review;
import com.shopmart.repository.ReviewRepository;

@Service
public class ReviewService {

	@Autowired private ReviewRepository reviewRepository;
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
	public List<ReviewResponse> get(String productId){
		return userService.jdbc.query("SELECT users.name, reviews.product_id, reviews.review_txt, reviews.date_created, reviews.review_id, reviews.reply_to FROM reviews inner join users on users.user_id = reviews.user_id WHERE reviews.product_id = ?", new Object[] {productId}, new BeanPropertyRowMapper(ReviewResponse.class));
	}
	public void delete(String productId){
		userService.jdbc.update("delete from shopmart.reviews where product_id = ?", new Object[]{productId});
	}
}
