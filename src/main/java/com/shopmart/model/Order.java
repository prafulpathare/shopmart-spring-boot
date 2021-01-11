package com.shopmart.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "orders")
public class Order {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private long order_id;
	
    @OneToMany(mappedBy="order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItem> order_items;
    
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnore
    private Customer customer;
    
    @Column(name = "payed")
    private boolean payed = false;
    
    public enum PaymentOption {
    	MASTERCARD, PAYPAL, BANK_TRANSFER, CASH_ON_DELIVERY;
    }
    
    @Column(name = "payment_option") @NotNull
	@Enumerated(EnumType.STRING)
    private PaymentOption payment_option;

    public Order() {}
    
	public Order(
		boolean payed,
		PaymentOption payment_option,
		Customer customer
	) {
		this.payed = payed;
		this.payment_option = payment_option;
		this.customer = customer;
	}

	public long getOrder_id() {
		return order_id;
	}

	public void setOrder_id(long order_id) {
		this.order_id = order_id;
	}

	public Set<OrderItem> getOrder_items() {
		return order_items;
	}

	public void setOrder_items(Set<OrderItem> order_items) {
		this.order_items = order_items;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public boolean isPayed() {
		return payed;
	}

	public void setPayed(boolean payed) {
		this.payed = payed;
	}

	public PaymentOption getPayment_option() {
		return payment_option;
	}

	public void setPayment_option(PaymentOption payment_option) {
		this.payment_option = payment_option;
	}

	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", order_items=" + order_items + ", customer=" + customer + ", payed="
				+ payed + ", payment_option=" + payment_option + "]";
	}
}
