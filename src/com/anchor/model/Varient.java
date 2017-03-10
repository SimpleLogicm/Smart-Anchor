package com.anchor.model;

public class Varient {

	int product_id,scheme_id;
	String variant,product_unit,status;
	float mrp,rp;
	
	
	public Varient() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Varient(int product_id, int scheme_id, String variant,
			String product_unit, String status, float mrp, float rp) {
		super();
		this.product_id = product_id;
		this.scheme_id = scheme_id;
		this.variant = variant;
		this.product_unit = product_unit;
		this.status = status;
		this.mrp = mrp;
		this.rp = rp;
	}


	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public int getScheme_id() {
		return scheme_id;
	}


	public void setScheme_id(int scheme_id) {
		this.scheme_id = scheme_id;
	}


	public String getVariant() {
		return variant;
	}


	public void setVariant(String variant) {
		this.variant = variant;
	}


	public String getProduct_unit() {
		return product_unit;
	}


	public void setProduct_unit(String product_unit) {
		this.product_unit = product_unit;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public float getMrp() {
		return mrp;
	}


	public void setMrp(float mrp) {
		this.mrp = mrp;
	}


	public float getRp() {
		return rp;
	}


	public void setRp(float rp) {
		this.rp = rp;
	}


	@Override
	public String toString() {
		return "Varient [product_id=" + product_id + ", scheme_id=" + scheme_id
				+ ", variant=" + variant + ", product_unit=" + product_unit
				+ ", status=" + status + ", mrp=" + mrp + ", rp=" + rp + "]";
	}
	
	
	
	
}
