package com.shopmart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "ordered_products")
public class OrderedProduct {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ordPrdId;
	@Column(name = "order_id") @NotNull
	private long orderId;
	@Column(name = "product_id") @NotNull
	private String productId;
	@Column(name = "quantity") @NotNull
	private int quantity;
	@Column(name = "product_price") @NotNull
	private int productPrice;
	@Column(name = "total_price") @NotNull
	private long totalPrice;

	public OrderedProduct() {
	}
	public OrderedProduct(long orderId, String productId, int quantity, int productPrice,
			long totalPrice) {
		this.orderId = orderId;
		this.productId = productId;
		this.quantity = quantity;
		this.productPrice = productPrice;
		this.totalPrice = totalPrice;
	}
	public long getOrdPrdId() {
		return ordPrdId;
	}
	public void setOrdPrdId(long ordPrdId) {
		this.ordPrdId = ordPrdId;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(int productPrice) {
		this.productPrice = productPrice;
	}
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String toString(){
		return this.ordPrdId+"_|_"+this.orderId+"_|_"+this.totalPrice+"_|_"
		+this.productPrice+"_|_"+this.quantity+"_|_"+this.productId;
	}
}
	