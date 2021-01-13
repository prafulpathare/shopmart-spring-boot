package com.shopmart.repository;

import org.springframework.data.repository.CrudRepository;

import com.shopmart.model.Product;

public interface ProductRepository extends CrudRepository<Product, String>{
	
}
