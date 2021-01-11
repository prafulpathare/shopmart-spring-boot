package com.shopmart.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends User {

    @OneToMany(mappedBy="customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Order> orders;
	
	public Customer() {}
	
	public Customer(String name, String password, String email, String contact, boolean is_profile, int status) {
		super(name, password, email, contact, is_profile, status);
	}

	public Customer(Set<Order> orders) {
		this.orders = orders;
	}
	
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
}
