package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.model.Review;
import com.shopmart.service.ReviewService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/review")
public class ReviewController {

	@Autowired private ReviewService reviewService;
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	@GetMapping(value = "/{productid}")
	public ResponseEntity<?> get(@PathVariable(name="productid") String productid){
		return ResponseEntity.status(200).body(
			reviewService.get(productid)
		);
	}
	
	@DeleteMapping(value = "/{productid}")
	public ResponseEntity<?> delete(@PathVariable(name="productid") String productid){
		reviewService.delete(productid);
		return ResponseEntity.status(200).body(null);
	}
	
	@PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody Review review) {
        reviewService.create(review);
        return ResponseEntity.status(200).body(null);
    }
	
}