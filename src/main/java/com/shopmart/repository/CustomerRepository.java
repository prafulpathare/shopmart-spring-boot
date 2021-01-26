package com.shopmart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopmart.model.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long>{

	@Query(value = "select * from users inner join customers on users.user_id = customers.user_id where users.email = :email limit 1", nativeQuery = true)
	Customer findByEmail(@Param(value = "email") String email);
	
	@Query(value = "select * from users inner join customers on users.user_id = customers.user_id where users.username = :username limit 1", nativeQuery = true)
	Customer findByUsername(@Param(value = "username") String username);
}
