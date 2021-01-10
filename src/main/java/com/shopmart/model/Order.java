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
@Table(name = "orders")
public class Order {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private long orderId;
	
	@Column(name = "total") @NotNull
	private long total;
	@Column(name = "delivery_address") @NotNull
	private String deliveryAddress;
	@Column(name = "payment_option") @NotNull
	private String paymentOption;
	@Column(name = "is_paid") @NotNull
	private boolean isPaid = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;
	
	public Order() {
	}
	public Order(long total, String deliveryAddress, String paymentOption, boolean isPaid, User user) {
		this.total = total;
		this.deliveryAddress = deliveryAddress;
		this.paymentOption = paymentOption;
		this.isPaid = isPaid;
		this.user = user;
	}
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getPaymentOption() {
		return paymentOption;
	}
	public void setPaymentOption(String paymentOption) {
		this.paymentOption = paymentOption;
	}
	public boolean isPaid() {
		return isPaid;
	}
	public void setPaid(boolean isPaid) {
		this.isPaid = isPaid;
	}
	public long getOrderId() {
		return orderId;
	}
	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", total=" + total + ", deliveryAddress=" + deliveryAddress
				+ ", paymentOption=" + paymentOption + ", isPaid=" + isPaid + ", user=" + user + "]";
	}
}