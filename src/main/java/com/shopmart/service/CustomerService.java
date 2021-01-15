package com.shopmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopmart.model.Customer;
import com.shopmart.repository.CustomerRepository;

@Service
public class CustomerService {
	@Autowired public UserService userService;
	@Autowired public CustomerRepository customerRepository;

	public Customer get() {
		return customerRepository.findByUsername(userService.getUsername());
	}
	public Customer getFromEmail(String email) {
		return customerRepository.findByEmail(email);
	}
	
	public void create(Customer customer) {
		customer.setPassword(userService.bcryPasswordEncoder.encode(customer.getPassword()));
		customerRepository.save(customer);
	}
	
}
