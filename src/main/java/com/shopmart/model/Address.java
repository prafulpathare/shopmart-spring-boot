package com.shopmart.model;

import java.io.Serializable;

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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "addresses")
public class Address {
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "address_id")
	private long addressId;
	@Column(name = "line_one") @NotNull
	private String line_one;
	@Column(name = "line_two")
	private String line_two;
	@Column(name = "line_three") @NotNull
	private String line_three;
	@Column(name = "city") @NotNull
	private String city;
	@Column(name = "state") @NotNull
	private String state;
	@Column(name = "pincode", length = 6) @NotNull
	private String pincode;
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

	public Address() { }
	
	public Address(String line_one, String line_two, String line_three, String city, String state,
			String pincode, User user) {
		this.line_one = line_one;
		this.line_two = line_two;
		this.line_three = line_three;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.user = user;
	}

	public long getAddressId() {
		return addressId;
	}

	public void setAddressId(long addressId) {
		this.addressId = addressId;
	}

	public String getLine_one() {
		return line_one;
	}

	public void setLine_one(String line_one) {
		this.line_one = line_one;
	}

	public String getLine_two() {
		return line_two;
	}

	public void setLine_two(String line_two) {
		this.line_two = line_two;
	}

	public String getLine_three() {
		return line_three;
	}

	public void setLine_three(String line_three) {
		this.line_three = line_three;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address [address_id=" + addressId + ", line_one=" + line_one + ", line_two=" + line_two + ", line_three="
				+ line_three + ", city=" + city + ", state=" + state + ", pincode=" + pincode
				+ "]";
	}	
}
