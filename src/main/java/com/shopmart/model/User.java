package com.shopmart.model;

import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private long user_id;

	@Column(name = "username", unique = true) @NotNull
	private String username;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "password")
	@NotNull
	@JsonIgnore
	private String password;
	
	@Column(name = "email", unique = true)
	@NotNull
	private String email;
	
	@Column(name = "contact", unique = true, length = 10)
	private String contact;
	
	@Column(name = "is_profile")
	@NotNull
	private boolean is_profile = false;

	@Column(name = "status")
	@NotNull
	private int status = 0;

    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Address> addresses;
    
    @OneToMany(mappedBy="user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Review> reviews;
	
	public User() { }

	public User(String name, String password, String email, String contact, boolean is_profile, int status) {
		this.username = UUID.randomUUID().toString();
		this.name = name;
		this.password = password;
		this.email = email;
		this.contact = contact;
		this.is_profile = is_profile;
		this.status = status;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isIs_profile() {
		return is_profile;
	}

	public void setIs_profile(boolean is_profile) {
		this.is_profile = is_profile;
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
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", password=" + password + ", email=" + email
				+ ", contact=" + contact + "]";
	}
}
