package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.config.JwtTokenUtil;
import com.shopmart.dto.JwtRequest;
import com.shopmart.dto.JwtResponse;
import com.shopmart.dto.RegisterRequest;
import com.shopmart.model.Address;
import com.shopmart.model.Customer;
import com.shopmart.model.Password;
import com.shopmart.model.User;
import com.shopmart.repository.PasswordRepository;
import com.shopmart.repository.UserRepository;
import com.shopmart.service.AddressService;
import com.shopmart.service.EmailService;
import com.shopmart.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/address")
public class AddressController {

	@Autowired private AddressService addressService;
	@Autowired private JdbcTemplate jdbc;	
	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);


	@PostMapping(value = "")
	public ResponseEntity<?> create(@RequestBody Address address) {
		addressService.create(address);
		return ResponseEntity.status(200).body(null);
	}
	@DeleteMapping(value = "")
	public ResponseEntity<?> delete(@RequestBody Address address) {
		addressService.delete(address.getAddress_id());
		return ResponseEntity.status(200).body(null);
	}
}
