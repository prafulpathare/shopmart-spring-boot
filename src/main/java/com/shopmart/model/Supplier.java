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

    @OneToMany(mappedBy="supplier", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Product> products; 
	
	public enum SupplierType {
		INDIVIDUAL, BUSSINESS
	}
	
	@Column(name = "supplier_type")
	@Enumerated(EnumType.STRING)
	private SupplierType supplier_type;

	@Column(name = "business_name") @NotNull
	private String business_name;

	@Column(length = 10) @NotNull
	private String pan;
	
	@Column(name = "is_verified") @NotNull
	private boolean is_verified;

	@Column(length = 15) @NotNull
	private String gst;

	public Supplier() {}
	
	public Supplier(String name, String business_name, String password, String email, boolean is_verified, String contact, boolean is_profile, int status, SupplierType supplierType, String pan, String gst) {
		super(name, password, email, contact, is_profile, status);
		this.supplier_type = supplier_type;
		this.business_name = business_name;
		this.gst = gst;
		this.pan = pan;
		this.is_verified = is_verified;
	}
	
	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
	}

	public boolean isIs_verified() {
		return is_verified;
	}

	public void setIs_verified(boolean is_verified) {
		this.is_verified = is_verified;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public SupplierType getSupplier_type() {
		return supplier_type;
	}

	public void setSupplier_type(SupplierType supplier_type) {
		this.supplier_type = supplier_type;
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
