package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	@Autowired private ReviewService reviewServ;
	private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);
	
	@GetMapping(value = "/{productid}")
	public ResponseEntity<?> getReviewByProduct(@PathVariable(name="productid") String productid){
		return ResponseEntity.status(200).body(null);
	}
	@PostMapping(value = "")
	public ResponseEntity<?> addReview(@RequestBody Review review){
		return ResponseEntity.status(200).body(null);
	}
	@PostMapping(value = "/reply/add")
	public ResponseEntity<?> addReply(@RequestBody Review review){
		return ResponseEntity.status(200).body(null);
	}
}