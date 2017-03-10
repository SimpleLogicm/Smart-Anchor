package com.anchor.model;

public class City {

	
	String desc,id,status,state_ID,city_code;

	
	public City() {
		super();
		// TODO Auto-generated constructor stub
	}


	

	public City(String desc, String id, String status, String state_ID,
			String city_code) {
		super();
		this.desc = desc;
		this.id = id;
		this.status = status;
		this.state_ID = state_ID;
		this.city_code = city_code;
	}




	public String getDesc() {
		return desc;
	}


	public void setDesc(String desc) {
		this.desc = desc;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getState_ID() {
		return state_ID;
	}


	public void setState_ID(String state_ID) {
		this.state_ID = state_ID;
	}



	

	public String getCity_code() {
		return city_code;
	}




	public void setCity_code(String city_code) {
		this.city_code = city_code;
	}




	@Override
	public String toString() {
		return "City [desc=" + desc + ", id=" + id + ", status=" + status
				+ ", state_ID=" + state_ID + ", city_code=" + city_code + "]";
	}


	
	
	
	
}
