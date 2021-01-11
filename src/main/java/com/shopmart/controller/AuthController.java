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
import com.shopmart.repository.AddressRepository;
import com.shopmart.repository.PasswordRepository;
import com.shopmart.repository.UserRepository;
import com.shopmart.service.EmailService;
import com.shopmart.service.PasswordService;
import com.shopmart.service.UserService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private UserRepository userRepository;
	@Autowired private AddressRepository addressRepository;
	@Autowired private PasswordRepository passwordRepository;
	@Autowired private EmailService emailService;
	@Autowired private UserService userService;
	@Autowired private PasswordService passwordService;
	@Autowired private PasswordEncoder bcryptEncoder;
	@Autowired private JdbcTemplate jdbc;	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		System.out.println(authenticationRequest.getEmail());
		User user = userRepository.findByEmail(authenticationRequest.getEmail());
		if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				
		authenticate(user.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PostMapping(value = "/register")
	public ResponseEntity<?> saveCustomer(@RequestBody RegisterRequest req) throws Exception {
		if(userRepository.findByEmail(req.getEmail()) != null) return ResponseEntity.status(HttpStatus.FOUND).body("EMAIL_EXISTS");
		Password password = passwordRepository.findByIdAndEmail(req.getToken(), req.getEmail());
		if(password == null) return ResponseEntity.status(HttpStatus.FOUND).body("INVALID_TOKEN");
		req.setEmail(password.getEmail());
		
		User user = userRepository.save(new Customer(
			req.getName(), bcryptEncoder.encode(req.getPassword()),
			req.getEmail(), req.getContact(), false, 1
		));

		passwordRepository.delete(password);
		return ResponseEntity.ok(user);
	}

	@PostMapping(value = "/update")
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		return ResponseEntity.status(200).body(null);
	}
	
	@PostMapping(value = "/address")
	public ResponseEntity<?> updateUser(@RequestBody Address address) {
		userService.addAddress(address);
		return ResponseEntity.status(200).body(null);
	}
	
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}