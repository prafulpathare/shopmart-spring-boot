package com.shopmart.dto;

public class RegisterRequest {

	private String email, password, contact, name;
	private long token;
	
	public RegisterRequest() { 	}

	public RegisterRequest(String email, String password, String contact, String name, long token) {
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.name = name;
		this.token = token;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getToken() {
		return token;
	}

	public void setToken(long token) {
		this.token = token;
	}	
}
