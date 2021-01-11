package com.shopmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopmart.model.Supplier;
import com.shopmart.repository.SupplierRepository;


@Service
public class SupplierService {
	@Autowired private UserService userService;
	@Autowired private SupplierRepository supplierRepository;
	
	public Supplier getSupplier() {
		return supplierRepository.findByUsername(userService.getUsername());
	}
}
