package com.shopmart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopmart.model.Supplier;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Long>{

	@Query(value = "select * from users inner join suppliers on users.user_id = suppliers.user_id where users.email = :email limit 1", nativeQuery = true)
	Supplier findByEmail(@Param(value = "email") String email);
	
	@Query(value = "select * from users inner join suppliers on users.user_id = suppliers.user_id where users.username = :username limit 1", nativeQuery = true)
	Supplier findByUsername(@Param(value = "username") String username);
}
