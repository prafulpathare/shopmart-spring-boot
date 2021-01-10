package com.shopmart.controller;

import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.repository.PasswordRepository;
import com.shopmart.repository.UserRepository;
import com.shopmart.service.UserService;
import com.shopmart.util.AuthPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/util")
public class UtilController {

	@Autowired private UserService userDetailsService;
	@Autowired private UserRepository userRepo;
	@Autowired private PasswordRepository pswdRepo;
	private static final Logger log = LoggerFactory.getLogger(UtilController.class);


	@PostMapping(value = "/setpassword", produces = "application/json")
	public ResponseEntity<?> setPassword(@RequestBody AuthPassword pswd) throws Exception {
		boolean isSet = userDetailsService.setPassword(pswd);
		if(isSet) {
			return ResponseEntity.ok("{\"status\", 200, \"message\", \"UPDATED\"}");
		}
		else {
			log.info("Password not updated for "+pswd);
			throw new Exception("Password Updation FAILED");
		}
	}
}