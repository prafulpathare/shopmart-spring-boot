package com.shopmart.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sun.istack.NotNull;


@Entity
@Table(name = "passwords")
public class Password {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int passwordid;
	
	@Column(name = "email")
	@NotNull
	private String email;

	public Password() {
	}
	public Password(String email) {
		this.email = email;
	}
	public Integer getPasswordid() {
		return passwordid;
	}
	public void setPasswordid(Integer passwordid) {
		this.passwordid = passwordid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}