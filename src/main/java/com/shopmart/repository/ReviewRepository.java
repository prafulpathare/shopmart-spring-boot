package com.shopmart.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopmart.model.Review;

public interface ReviewRepository  extends CrudRepository<Review, Long>{
	
	@Query(value = "SELECT users.name, reviews.product_id, reviews.review_txt, reviews.date_created, reviews.review_id, reviews.reply_to FROM reviews inner join users on users.user_id = reviews.user_id WHERE reviews.product_id = :productid", nativeQuery = true)
	Set<Review> findByProductId(@Param(value = "productid") String productId);
}
