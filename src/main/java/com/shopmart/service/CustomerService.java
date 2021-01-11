package com.shopmart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopmart.model.Customer;
import com.shopmart.model.Order;
import com.shopmart.model.OrderItem;
import com.shopmart.repository.CustomerRepository;
import com.shopmart.repository.OrderItemRepository;
import com.shopmart.repository.OrderRepository;

@Service
public class CustomerService {
	@Autowired private UserService userService;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderItemRepository orderItemRepository;
	
	public Customer getCustomer() {
		return customerRepository.findByUsername(userService.getUsername());
	}

	public void addOrder(Order order) {
		if(order.getOrder_items() == null || order.getOrder_items().size() < 1) return;
		Customer customer = customerRepository.findByUsername(userService.getUsername());
		Order o = orderRepository.save(new Order(order.isPayed(), order.getPayment_option(), customer));
		for(OrderItem item : order.getOrder_items()) {
			item.setTotal(Math.round(item.getPrice() * item.getQuantity() * 100.0)/100.0);
			item.setOrder(o);
			orderItemRepository.save(item);
		}
	}
}
