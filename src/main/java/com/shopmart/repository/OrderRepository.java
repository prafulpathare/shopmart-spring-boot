package com.shopmart.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shopmart.model.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	@Query(value = "SELECT sum(total) FROM order_items WHERE order_id = :order_id", nativeQuery = true)
	double getOrderTotal(@Param(value = "order_id") long orderId);
}
