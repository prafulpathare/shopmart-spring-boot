package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.config.JwtTokenUtil;
import com.shopmart.dto.CustomerRegisterRequest;
import com.shopmart.dto.JwtRequest;
import com.shopmart.dto.JwtResponse;
import com.shopmart.dto.SupplierRegisterRequest;
import com.shopmart.model.Customer;
import com.shopmart.model.Password;
import com.shopmart.model.Supplier;
import com.shopmart.repository.PasswordRepository;
import com.shopmart.service.SupplierService;
import com.shopmart.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {

	@Autowired private UserService userService;
	@Autowired private JwtTokenUtil jwtTokenUtil;
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@PutMapping(value = "/email")
	public ResponseEntity<?> updateEmail(@RequestBody String email)  {
		userService.jdbc.update("update shopmart.users set email = ? where username = ?", new Object[]{email, userService.getUsername()});
		return ResponseEntity.status(200).body(null);
	}

	@PutMapping(value = "/name")
	public ResponseEntity<?> updateName(@RequestBody String name)  {
		userService.jdbc.update("update shopmart.users set name = ? where username = ?", new Object[]{name, userService.getUsername()});
		return ResponseEntity.status(200).body(null);
	}

	@PutMapping(value = "/contact")
	public ResponseEntity<?> updateContact(@RequestBody String contact)  {
		userService.jdbc.update("update shopmart.users set contact = ? where username = ?", new Object[]{contact, userService.getUsername()});
		return ResponseEntity.status(200).body(null);
	}
	
}