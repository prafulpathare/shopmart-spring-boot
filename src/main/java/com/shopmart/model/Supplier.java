package com.shopmart.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name = "suppliers")
public class Supplier extends User {

//    @OneToMany(mappedBy="retailer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private Set<Order> orders;

    @OneToMany(mappedBy="supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Product> products; 
	
	public enum SupplierType {
		INDIVIDUAL, BUSSINESS
	}
	
	@Column
	@Enumerated(EnumType.STRING)
	private SupplierType supplierType;

	@Column(length = 10) @NotNull
	private String pan;	

	@Column(length = 15) @NotNull
	private String gst;

	public Supplier() {}
	
	public Supplier(
		String name, String password, String email, String contact, boolean is_profile, int status,
		SupplierType supplierType,
		String pan, String gst
	) {
		super(name, password, email, contact, is_profile, status);
		this.supplierType = supplierType;
		this.gst = gst;
		this.pan = pan;
	}

	public SupplierType getSupplierType() {
		return supplierType;
	}

	public void setSupplierType(SupplierType supplierType) {
		this.supplierType = supplierType;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getGst() {
		return gst;
	}

	public void setGst(String gst) {
		this.gst = gst;
	}	
	
}
