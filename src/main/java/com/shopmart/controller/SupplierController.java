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

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/supplier")
public class SupplierController {

	@Autowired private PasswordRepository passwordRepository;
	@Autowired private SupplierService supplierService;	
	@Autowired private JwtTokenUtil jwtTokenUtil;
	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

	@GetMapping(value = "")
	public ResponseEntity<?> getSupplier() {
		return ResponseEntity.ok(supplierService.get());
	}
	
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
		System.out.println(supplier.toString());
		if(supplier == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
				
		supplierService.userService.authenticate(supplier.getUsername(), req.getPassword());
		final UserDetails userDetails = supplierService.userService.loadUserByUsername(supplier.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@PutMapping(value = "/business-name")
	public ResponseEntity<?> updateBusinessName(@RequestBody String businessName) {
		supplierService.userService.jdbc.update("update shopmart.suppliers set business_name = ? where username = ?", new Object[] {businessName, supplierService.userService.getUsername()});
		return ResponseEntity.status(200).body(null);
	}
	
	@PutMapping(value = "/supplier-type")
	public ResponseEntity<?> updateSupplierType(@RequestBody String supplierType) {
		if(supplierType.equals("BUSINESS") || supplierType.equals("INDIVIDUAL")) {			
			supplierService.userService.jdbc.update("update shopmart.suppliers set business_name = ? where username = ?", new Object[] {supplierType, supplierService.userService.getUsername()});
			return ResponseEntity.status(200).body(null);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);		
	}
	
	@PutMapping(value = "/gst")
	public ResponseEntity<?> updateGST(@RequestBody String gst) {
		supplierService.userService.jdbc.update("update shopmart.suppliers set gst = ? where username = ?", new Object[] {gst, supplierService.userService.getUsername()});
		return ResponseEntity.status(200).body(null);
	}

	@PutMapping(value = "/pan")
	public ResponseEntity<?> updatePAN(@RequestBody String pan) {
		supplierService.userService.jdbc.update("update shopmart.suppliers set pan = ? where username = ?", new Object[] {pan, supplierService.userService.getUsername()});
		return ResponseEntity.status(200).body(null);
	}

	// can accessed by admin
	@GetMapping(value = "/approve/{supplierId}")
	public ResponseEntity<?> approveSupplier(@PathVariable long supplierId) {
		supplierService.userService.jdbc.update("update shopmart.suppliers set is_verified = ? where user_id = ?", new Object[] {true, supplierId});
		return ResponseEntity.status(200).body(null);
	}
}