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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/supplier")
public class SupplierController {

	@Autowired private PasswordRepository passwordRepository;
	@Autowired private SupplierService supplierService;	
	@Autowired private JwtTokenUtil jwtTokenUtil;
	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

	@PostMapping(value = "/register")
	public ResponseEntity<?> saveCustomer(@RequestBody SupplierRegisterRequest req) throws Exception {
		
		if(supplierService.userService.emailExists(req.getEmail()))  return ResponseEntity.status(HttpStatus.FOUND).body("EMAIL_EXISTS");
		
		Password password = passwordRepository.findByIdAndEmail(req.getToken(), req.getEmail());
		if(password == null) return ResponseEntity.status(HttpStatus.FOUND).body("INVALID_TOKEN");
		req.setEmail(password.getEmail());
		
		supplierService.create(new Supplier(
			req.getName(), req.getBusiness_name(), req.getPassword(), req.getEmail(), false, req.getContact(),
			false, 1, req.getSupplier_type(), req.getPan(), req.getGst()
		));

		passwordRepository.delete(password);
		return ResponseEntity.status(200).body(null);
	}
	
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest req) throws Exception {
		
		Supplier supplier = supplierService.getFromEmail(req.getEmail());
		if(supplier == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				
		supplierService.userService.authenticate(supplier.getUsername(), req.getPassword());
		final UserDetails userDetails = supplierService.userService.loadUserByUsername(supplier.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	
	@GetMapping(value = "")
	public ResponseEntity<?> getSupplier() {
		return ResponseEntity.ok(supplierService.get());
	}
}