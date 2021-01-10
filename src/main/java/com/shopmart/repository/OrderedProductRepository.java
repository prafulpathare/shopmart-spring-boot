package com.shopmart.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shopmart.model.OrderedProduct;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


@Repository
public interface OrderedProductRepository extends CrudRepository<OrderedProduct, Long>{


	@Query(value = "Select * from ordered_products where order_id = :orderid", nativeQuery = true)
	List<OrderedProduct> findByOrderId(@Param(value = "orderid") Long orderid);
}
