package com.anchor.model;

public class Retailer {

	int retailer_id,state_id,city_id,beat_id;
	String retailer_name,shop_name,address,street,landmark,landline_no,email_id,contact_name,created_date,status,vatin,retailer_code,pincode;
	
	
	public Retailer() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	
	

	public Retailer(int retailer_id, int state_id, int city_id, int beat_id,
			String pincode, String retailer_name, String shop_name,
			String address, String street, String landmark, String landline_no,
			String email_id, String contact_name, String created_date,
			String status, String vatin, String retailer_code) {
		super();
		this.retailer_id = retailer_id;
		this.state_id = state_id;
		this.city_id = city_id;
		this.beat_id = beat_id;
		this.pincode = pincode;
		this.retailer_name = retailer_name;
		this.shop_name = shop_name;
		this.address = address;
		this.street = street;
		this.landmark = landmark;
		this.landline_no = landline_no;
		this.email_id = email_id;
		this.contact_name = contact_name;
		this.created_date = created_date;
		this.status = status;
		this.vatin = vatin;
		this.retailer_code = retailer_code;
	}







	public String getVatin() {
		return vatin;
	}


	public void setVatin(String vatin) {
		this.vatin = vatin;
	}


	public String getStatus() {
		return status;
	}





	public void setStatus(String status) {
		this.status = status;
	}



	public int getRetailer_id() {
		return retailer_id;
	}


	public void setRetailer_id(int retailer_id) {
		this.retailer_id = retailer_id;
	}


	public int getState_id() {
		return state_id;
	}


	public void setState_id(int state_id) {
		this.state_id = state_id;
	}


	public int getCity_id() {
		return city_id;
	}


	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}


	public int getBeat_id() {
		return beat_id;
	}


	public void setBeat_id(int beat_id) {
		this.beat_id = beat_id;
	}


	public String getPincode() {
		return pincode;
	}


	public void setPincode(String pincode) {
		this.pincode = pincode;
	}


	public String getRetailer_name() {
		return retailer_name;
	}


	public void setRetailer_name(String retailer_name) {
		this.retailer_name = retailer_name;
	}


	public String getShop_name() {
		return shop_name;
	}


	public void setShop_name(String shop_name) {
		this.shop_name = shop_name;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getLandmark() {
		return landmark;
	}


	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}


	public String getLandline_no() {
		return landline_no;
	}


	public void setLandline_no(String landline_no) {
		this.landline_no = landline_no;
	}


	public String getEmail_id() {
		return email_id;
	}


	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}


	public String getContact_name() {
		return contact_name;
	}


	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}




	public String getCreated_date() {
		return created_date;
	}




	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}







	public String getRetailer_code() {
		return retailer_code;
	}







	public void setRetailer_code(String retailer_code) {
		this.retailer_code = retailer_code;
	}







	@Override
	public String toString() {
		return "Retailer [retailer_id=" + retailer_id + ", state_id="
				+ state_id + ", city_id=" + city_id + ", beat_id=" + beat_id
				+ ", pincode=" + pincode + ", retailer_name=" + retailer_name
				+ ", shop_name=" + shop_name + ", address=" + address
				+ ", street=" + street + ", landmark=" + landmark
				+ ", landline_no=" + landline_no + ", email_id=" + email_id
				+ ", contact_name=" + contact_name + ", created_date="
				+ created_date + ", status=" + status + ", vatin=" + vatin
				+ ", retailer_code=" + retailer_code + "]";
	}


	

	
	
}
