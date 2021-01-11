package com.shopmart.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopmart.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>{
	
	@Query(value = "SELECT * FROM addresses WHERE user_id = :user_id", nativeQuery = true)
	Set<Address> findByUserId(@Param(value = "user_id") long user_id);
	
}
