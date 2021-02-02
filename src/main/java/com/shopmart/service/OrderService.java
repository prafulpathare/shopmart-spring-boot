package com.shopmart.service;

import static com.mongodb.client.model.Filters.eq;

import java.util.Date;

import org.bson.Document;
import org.bson.types.ObjectId;
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
	@Autowired private com.shopmart.config.MongoConfig mongoConfig;
		
	private OrderItem getMongoProductById(String orderItemId) {
		Document obj = mongoConfig.productCollection().find(eq("_id", new ObjectId(orderItemId))).first();
		
		double price = 0.0;
		try {
			price = obj.getDouble("price");
		}
		catch(ClassCastException e) {
			price = obj.getInteger("price");
		}
		
		return new OrderItem(orderItemId, obj.getString("name"), price, 1, null);
	}

	public void create(Order order) {
		try {
			if(order.getOrder_items() == null || order.getOrder_items().size() < 1)
				return;		
			if(order.getPayment_option() == PaymentOption.CASH_ON_DELIVERY && order.isPayed() == true)
				throw new CashOnDeliveryAndPayedException("invalid condition CASH-ON-DELIVERY and isPayed");
			order.setDate_created(new Date());
			
			Customer customer = customerRepository.findByUsername(userService.getUsername());

			order.getAddress().setUser((User) customer);
			Address address = addressRepository.save(order.getAddress());
			
			Order o = orderRepository.save(new Order(order.isPayed(), order.getPayment_option(), customer, address, new Date()));
			o.setDelivery_date();
			
			double orderTotal = 0.0;
			o.setTotal(0.0);
			for(OrderItem item : order.getOrder_items()) {
				OrderItem tempItem = this.getMongoProductById(item.getOrder_item_id());
				item.setName(tempItem.getName());
				item.setPrice(tempItem.getPrice());
				item.setTotal(Math.round(item.getPrice() * item.getQuantity() * 100.0)/100.0);
				item.setOrder(o);
				orderItemRepository.save(item);
				
				orderTotal += item.getTotal();
			}
			o.setTotal(orderTotal);
			orderRepository.save(o);
		}
		catch (CashOnDeliveryAndPayedException e) {
			System.out.println(e.getMessage());			
		}
	}
	
	public void delete(long orderId) {
		userService.jdbc.update("delete from shopmart.order_items where order_id = ?", new Object[] {orderId});
		userService.jdbc.update("delete from shopmart.orders where order_id = ?", new Object[] {orderId});
	}
}
