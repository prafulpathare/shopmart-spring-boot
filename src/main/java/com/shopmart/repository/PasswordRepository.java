package com.shopmart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shopmart.model.Password;

public interface PasswordRepository extends CrudRepository<Password, Integer>{

	@Query(value = "select * from passwords where passwordid = :pswdid LIMIT 1", nativeQuery = true)
	Password findByPasswordId(@Param(value = "pswdid") Integer pswdid);
	
	@Query(value = "select * from passwords where email = :email LIMIT 1", nativeQuery = true)
	Password findByEmail(@Param(value = "email") String email);
	
	@Query(value = "delete from passwords where email = :email", nativeQuery = true)
	Integer deleteByEmail(@Param(value = "email") String email);
}
