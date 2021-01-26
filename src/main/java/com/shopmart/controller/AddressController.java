package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.model.Address;
import com.shopmart.service.AddressService;

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
