package com.shopmart.controller;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.model.Password;
import com.shopmart.repository.PasswordRepository;
import com.shopmart.repository.UserRepository;
import com.shopmart.service.EmailService;
import com.shopmart.service.PasswordService;
import com.shopmart.util.Mail;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/utils")
public class UtilController {

	@Autowired private UserRepository userRepository;
	@Autowired private PasswordRepository passwordRepository;
	@Autowired private EmailService emailService;
	@Autowired private PasswordService passwordService;
	@Autowired private JdbcTemplate jdbc;
	private static final Logger logger = LoggerFactory.getLogger(UtilController.class);

	@PostMapping(value = "/email-verification")
	public ResponseEntity<?> emailVerification(@RequestBody String email){
		System.out.println("email verification -> " + email);
		jdbc.update("delete from passwords where email = ?", email);
		
		if(userRepository.findByEmail(email)  != null) return ResponseEntity.status(HttpStatus.FOUND).body("USER_EXISTS");
		Password password = passwordRepository.save(new Password(email.trim()));
		Mail mail = new Mail(email.trim(), "email-verification", "Shopmart | Activate account", null,
			new HashMap<String, Object>() {{
				put("token", password.getPasswordId());
			}}
		);
		emailService.sendEmail(mail);
		
		return ResponseEntity.ok(null);
	}
}