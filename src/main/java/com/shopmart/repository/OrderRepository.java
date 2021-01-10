package com.shopmart.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopmart.model.Order;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long>{
	

	@Query(value = "Select * from orders where user_id = :user_id", nativeQuery = true)
	List<Order> findByUserId(@Param(value = "user_id") Long user_id);
}
