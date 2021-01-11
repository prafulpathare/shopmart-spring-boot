package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.model.Order;
import com.shopmart.service.CustomerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired private CustomerService customerService;
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@GetMapping(value = "")
	public ResponseEntity<?> getCustomer() {
		return ResponseEntity.ok(customerService.getCustomer());
	}
	
	@PostMapping(value = "/order")
	public ResponseEntity<?> addOrders(@RequestBody Order order) {
		System.out.println(order.toString());
		customerService.addOrder(order);
		return ResponseEntity.status(200).body(null);
	}
	
	
}