package com.shopmart.controller;

import java.util.List;
import java.util.ArrayList;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.http.ResponseEntity;
import org.apache.logging.log4j.LogManager;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.shopmart.model.Address;
import com.shopmart.model.Order;
import com.shopmart.model.OrderedProduct;
import com.shopmart.repository.OrderRepository;
import com.shopmart.repository.OrderedProductRepository;
import com.shopmart.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/order")
public class OrderController {

	@Autowired private UserService userServ;
	@Autowired private OrderRepository orderRepo;
	@Autowired private OrderedProductRepository orderedPrdRepo;
	@Autowired private JdbcTemplate jdbc;
	@Autowired private MongoDatabase mongodb;
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

	
	@GetMapping(value = "")
	public ResponseEntity<?> getOrders() {
		List<GetOrder> orders = new ArrayList<>();

		for(Order ord: orderRepo.findByUserId(userServ.getUserId())){
			GetOrder getOrd = new GetOrder();
			getOrd.orderid = ord.getOrderId();
			getOrd.total = ord.getTotal();
			getOrd.delivery_address = ord.getDeliveryAddress();
			getOrd.orderedPrds = orderedPrdRepo.findByOrderId(ord.getOrderId());
			orders.add(getOrd);
		}

		return ResponseEntity.ok(orders);
	}
	@PostMapping(value = "/checkout")
	public Order checkout(@RequestBody CheckOut checkOut) {
		
		MongoCollection<Document> collection = mongodb.getCollection("products");
		Order order = orderRepo.save(
			new Order(
					0, checkOut.delivery_address,
					"VISA", false, userServ.getUser()
			)
		);
		long total = 0;
		for (CheckOutItem item : checkOut.items) {
			Document doc = collection.find(Filters.eq("_id", new ObjectId(item.productId))).first();
			OrderedProduct ordPrd = orderedPrdRepo.save(
					new OrderedProduct(
							order.getOrderId(),
							doc.getObjectId("_id").toString(),
							item.quantity,
							doc.getInteger("price"),
							(doc.getInteger("price") * item.quantity))
			);
			total += doc.getInteger("price") * item.quantity;
		}
		order.setTotal(total);
		return orderRepo.save(order);
	}
	
	@PostMapping(value="/delete")
	public void deleteOrder(@RequestBody Long orderid){
		jdbc.update("delete from orders where orderid = ?", orderid);
		jdbc.update("delete from ordered_products where order_id = ?", orderid);
	}
}

class CheckOutItem{
	public String productId;
	public int quantity;
}
class CheckOut {
	public List<CheckOutItem> items;
	public String delivery_address;
}

class GetOrder{
	public long orderid;
	public long total = 0;
	public String delivery_address;
	public List<OrderedProduct> orderedPrds;
}