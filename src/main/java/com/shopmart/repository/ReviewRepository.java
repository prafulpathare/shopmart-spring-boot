package com.shopmart.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopmart.model.Review;

public interface ReviewRepository  extends CrudRepository<Review, Long>{
	
	@Query(value = "SELECT * FROM reviews WHERE product_id = :productid", nativeQuery = true)
	Set<Review> findByProductId(@Param(value = "productid") String productId);
}
