package com.shopmart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopmart.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	@Query(value = "select * , 0 AS clazz_ from users where user_id= :user_id LIMIT 1", nativeQuery = true)
	User findByUserId(@Param(value = "user_id") long user_id);

	@Query(value = "select * , 0 AS clazz_ from users where username = :username limit 1", nativeQuery = true)
	User findByUsername(@Param(value = "username") String username);
	
	@Query(value = "select * , 0 AS clazz_ from users where email = :email limit 1", nativeQuery = true)
	User findByEmail(@Param(value = "email") String email);
	
	@Query(value = "select user_id from users where username = :username limit 1", nativeQuery = true)
	Long getUserIdFromUsername(@Param(value = "username") String username);
}
