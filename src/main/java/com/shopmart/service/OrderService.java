package com.shopmart.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopmart.exception.CashOnDeliveryAndPayedException;
import com.shopmart.model.Address;
import com.shopmart.model.Address.AddressType;
import com.shopmart.model.Customer;
import com.shopmart.model.Order;
import com.shopmart.model.Order.PaymentOption;
import com.shopmart.model.OrderItem;
import com.shopmart.model.User;
import com.shopmart.repository.AddressRepository;
import com.shopmart.repository.CustomerRepository;
import com.shopmart.repository.OrderItemRepository;
import com.shopmart.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired private UserService userService;
	@Autowired private CustomerRepository customerRepository;
	@Autowired private AddressRepository addressRepository;
	@Autowired private OrderRepository orderRepository;
	@Autowired private OrderItemRepository orderItemRepository;

	public void create(Order order) {
		try {
			if(order.getOrder_items() == null || order.getOrder_items().size() < 1)
				return;		
			if(order.getPayment_option() == PaymentOption.CASH_ON_DELIVERY && order.isPayed() == true)
				throw new CashOnDeliveryAndPayedException("invalid condition CASH-ON-DELIVERY and isPayed");
			order.getAddress().setAddress_type(AddressType.DELIVERY);
			order.setDate_created(new Date());
			
			Customer customer = customerRepository.findByUsername(userService.getUsername());

			order.getAddress().setUser((User) customer);
			Address address = addressRepository.save(order.getAddress());
			
			Order o = orderRepository.save(new Order(order.isPayed(), order.getPayment_option(), customer, address, new Date()));
			o.setDelivery_date();
			for(OrderItem item : order.getOrder_items()) {
				item.setTotal(Math.round(item.getPrice() * item.getQuantity() * 100.0)/100.0);
				item.setOrder(o);
				orderItemRepository.save(item);
			}
		}
		catch (CashOnDeliveryAndPayedException e) {
			System.out.println(e.getMessage());			
		}
	}
	
	public void delete(long orderId) {
		orderRepository.deleteById(orderId);
	}
}
