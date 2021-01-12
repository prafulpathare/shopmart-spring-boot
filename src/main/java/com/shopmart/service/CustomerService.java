package com.shopmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopmart.model.Customer;
import com.shopmart.repository.CustomerRepository;
import com.shopmart.repository.OrderItemRepository;
import com.shopmart.repository.OrderRepository;

@Service
public class CustomerService {
	@Autowired public UserService userService;
	@Autowired public CustomerRepository customerRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderItemRepository orderItemRepository;
	
	public Customer getCustomer() {
		return customerRepository.findByUsername(userService.getUsername());
	}
	
	public void create(Customer customer) {
		customer.setPassword(userService.bcryPasswordEncoder.encode(customer.getPassword()));
		customerRepository.save(customer);
	}
	
}
