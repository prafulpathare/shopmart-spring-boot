package com.shopmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopmart.model.Customer;
import com.shopmart.model.Order;
import com.shopmart.model.OrderItem;
import com.shopmart.repository.CustomerRepository;
import com.shopmart.repository.OrderItemRepository;
import com.shopmart.repository.OrderRepository;

@Service
public class CustomerService {
	@Autowired private UserService userService;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderItemRepository orderItemRepository;
	
	public Customer getCustomer() {
		return customerRepository.findByUsername(userService.getUsername());
	}
}
