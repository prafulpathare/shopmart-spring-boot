package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.model.Order;
import com.shopmart.service.OrderService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired private OrderService orderService;
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


	@PostMapping(value = "")
	public ResponseEntity<?> createOrder(@RequestBody Order order) {
		orderService.create(order);
		return ResponseEntity.status(200).body(null);
	}
	@DeleteMapping(value = "/{orderId}")
	public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
		orderService.delete(orderId);
		return ResponseEntity.status(200).body(null);
	}
	
}