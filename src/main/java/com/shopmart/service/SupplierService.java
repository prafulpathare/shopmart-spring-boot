package com.shopmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopmart.model.Supplier;
import com.shopmart.repository.SupplierRepository;


@Service
public class SupplierService {
	@Autowired public UserService userService;
	@Autowired private SupplierRepository supplierRepository;
	
	public Supplier get() {
		return supplierRepository.findByUsername(userService.getUsername());
	}
	public Supplier getFromEmail(String email) {
		return supplierRepository.findByEmail(email);
	}
	

	public void create(Supplier supplier) {
		supplier.setPassword(userService.bcryPasswordEncoder.encode(supplier.getPassword()));
		supplierRepository.save(supplier);
	}
	
}
