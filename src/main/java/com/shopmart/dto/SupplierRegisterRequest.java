package com.shopmart.dto;

import com.shopmart.model.Supplier.SupplierType;

public class SupplierRegisterRequest extends RegisterRequest {
	private SupplierType supplier_type;
	private String pan, gst, business_name;

	public SupplierRegisterRequest() { }

	public SupplierRegisterRequest(String email, String business_name, String password, String contact, String name,
		SupplierType supplier_type, String gst, String pan,
		long token) {
		super(email, password, contact, name, token);
		this.supplier_type = supplier_type;
		this.business_name = business_name;
		this.gst = gst;
		this.pan = pan;		
	}

	public String getBusiness_name() {
		return business_name;
	}

	public void setBusiness_name(String business_name) {
		this.business_name = business_name;
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

	public SupplierType getSupplier_type() {
		return supplier_type;
	}

	public void setSupplier_type(SupplierType supplier_type) {
		this.supplier_type = supplier_type;
	}
	
	
}
