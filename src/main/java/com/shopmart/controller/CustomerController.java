package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.config.JwtTokenUtil;
import com.shopmart.dto.CustomerRegisterRequest;
import com.shopmart.dto.JwtRequest;
import com.shopmart.dto.JwtResponse;
import com.shopmart.model.Customer;
import com.shopmart.model.Password;
import com.shopmart.model.User;
import com.shopmart.repository.PasswordRepository;
import com.shopmart.service.CustomerService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/customer")
public class CustomerController {

	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private CustomerService customerService;
	@Autowired private PasswordRepository passwordRepository;
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@GetMapping
	public ResponseEntity<?> getCustomer() {
		return ResponseEntity.ok(customerService.get());
	}
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		return ResponseEntity.status(200).body(null);
	}
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest req) throws Exception {
		
		Customer customer = customerService.customerRepository.findByEmail(req.getEmail());
		if(customer == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				
		customerService.userService.authenticate(customer.getUsername(), req.getPassword());
		final UserDetails userDetails = customerService.userService.loadUserByUsername(customer.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> saveCustomer(@RequestBody CustomerRegisterRequest req) throws Exception {
		
		if(customerService.userService.emailExists(req.getEmail()))  return ResponseEntity.status(HttpStatus.FOUND).body("EMAIL_EXISTS");
		
		Password password = passwordRepository.findByIdAndEmail(req.getToken(), req.getEmail());
		if(password == null) return ResponseEntity.status(HttpStatus.FOUND).body("INVALID_TOKEN");
		req.setEmail(password.getEmail());
		
		customerService.create(new Customer(
			req.getName(), req.getPassword(), req.getEmail(), req.getContact(), false, 1
		));

		passwordRepository.delete(password);
		return ResponseEntity.status(200).body(null);
	}

	
}