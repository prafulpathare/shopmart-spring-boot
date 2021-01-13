package com.shopmart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopmart.service.SupplierService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/supplier")
public class SupplierController {

	@Autowired private SupplierService supplierService;	
	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

	@GetMapping(value = "")
	public ResponseEntity<?> getSupplier() {
		return ResponseEntity.ok(supplierService.get());
	}
}