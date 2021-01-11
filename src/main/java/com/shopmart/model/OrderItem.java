package com.shopmart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "order_items")
public class OrderItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_item_id")
	private long order_item_id;
	
	@Column(name = "name") @NotNull
	private String name;

	@Column(name = "price") @NotNull
	private double price = 0.0;

	@Column(name = "quantity") @NotNull
	private int quantity = 1;
	
	@Column(name = "total") @NotNull
	private double total;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
	private Order order;
	
	public OrderItem() { }
	
	public OrderItem(String name, double price, int quantity, Order order) {
		this.name = name;
		this.price = price;
		this.quantity = quantity;
		this.total = Math.round(this.price * this.quantity * 100.0)/100.0;
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "OrderItem [order_item_id=" + order_item_id + ", name=" + name + ", price=" + price + ", quantity="
				+ quantity + ", total=" + total + ", order=" + order + "]";
	}

}
