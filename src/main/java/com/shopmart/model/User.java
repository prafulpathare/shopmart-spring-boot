package com.shopmart.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long userId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "password")
	@NotNull
	private String password;
	
	@Column(name = "email", unique = true)
	@NotNull
	private String email;
	
	@Column(name = "contact", unique = true, length = 10)
	private String contact;
	
	@Column(name = "isprofile")
	@NotNull
	private Integer isprofile = 0;

	@Column
	@NotNull
	private Integer status;
	
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses;
	
	public User() { }

	public User(String name, String password, String email, String contact, Integer isprofile, Integer status) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.contact = contact;
		this.isprofile = isprofile;
		this.status = status;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getIsprofile() {
		return isprofile;
	}
	public void setIsprofile(Integer isprofile) {
		this.isprofile = isprofile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void addAddress(Address address) {
		this.addresses.add(address);
	}

	@Override
	public String toString() {
		return "User [user_id=" + userId + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", contact=" + contact + "]";
	}
}