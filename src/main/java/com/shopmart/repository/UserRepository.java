package com.shopmart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopmart.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	@Query(value = "select * from users where user_id= :user_id LIMIT 1", nativeQuery = true)
	User findByUserId(@Param(value = "user_id") Long user_id);
	
	@Query(value = "select * from users where email = :email", nativeQuery = true)
	User findByEmail(@Param(value = "email") String email);
}
