package com.shopmart.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.config.JwtTokenUtil;
import com.shopmart.model.Address;
import com.shopmart.model.JwtRequest;
import com.shopmart.model.JwtResponse;
import com.shopmart.model.Password;
import com.shopmart.model.User;
import com.shopmart.repository.AddressRepository;
import com.shopmart.repository.PasswordRepository;
import com.shopmart.repository.UserRepository;
import com.shopmart.service.EmailService;
import com.shopmart.service.PasswordService;
import com.shopmart.service.UserService;
import com.shopmart.util.Mail;
import com.shopmart.util.Response;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/user")
public class UserController {

	@Autowired private AuthenticationManager authenticationManager;
	@Autowired private JwtTokenUtil jwtTokenUtil;
	@Autowired private UserRepository userRepo;
	@Autowired private UserService userService;
	@Autowired private AddressRepository addressRepo;
	@Autowired private PasswordRepository pswdRepo;
	@Autowired private EmailService emailService;
	@Autowired private PasswordService pswdService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@GetMapping(value = "/")
	public ResponseEntity<?> getUser() throws Exception {
		return ResponseEntity.ok(userService.getUser());
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		User user = userRepo.findByEmail(authenticationRequest.getEmail());
		authenticate(""+user.getUserId(), authenticationRequest.getPassword());
		final UserDetails userDetails = userService
				.loadUserByUsername(""+user.getUserId());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {
		return ResponseEntity.ok(userService.save(user));
	}

	@PostMapping(value = "/add-email")
	public ResponseEntity<?> addEmail(@RequestBody String email){
		User user = userRepo.findByEmail(email);
		if(user == null) {
			pswdService.deleteByEmail(email);
			Password pswd = pswdRepo.save(new Password(email));
			if(pswd != null) {
				Map map = new HashMap<>();
				map.put("email", email);
				map.put("token", pswd.getPasswordid());
				emailService.sendEmail(new Mail(
					email,
					"customer_email_confirmation",
					"Email Confirmation | Shopmart",
					null,
						map
				));
				return ResponseEntity.ok(new Response(200, pswd.getEmail(), null));
			}
		}
		return ResponseEntity.ok(new Response(500, "User with "+email+" already exists", null));
	}
	
	@PostMapping(value = "/update")
	public ResponseEntity<User> updateUser(@RequestBody User user) throws Exception {
		return ResponseEntity.ok(userService.updateUser(user));
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