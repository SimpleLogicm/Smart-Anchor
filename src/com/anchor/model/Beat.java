package com.anchor.model;

public class Beat {

	String beat_id,beat_name,state_id,city_id,status;

	public Beat() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Beat(String beat_id, String beat_name, String state_id,
			String city_id, String status) {
		super();
		this.beat_id = beat_id;
		this.beat_name = beat_name;
		this.state_id = state_id;
		this.city_id = city_id;
		this.status = status;
	}

	public String getBeat_id() {
		return beat_id;
	}

	public void setBeat_id(String beat_id) {
		this.beat_id = beat_id;
	}

	public String getBeat_name() {
		return beat_name;
	}

	public void setBeat_name(String beat_name) {
		this.beat_name = beat_name;
	}

	public String getState_id() {
		return state_id;
	}

	public void setState_id(String state_id) {
		this.state_id = state_id;
	}

	public String getCity_id() {
		return city_id;
	}

	public void setCity_id(String city_id) {
		this.city_id = city_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Beat [beat_id=" + beat_id + ", beat_name=" + beat_name
				+ ", state_id=" + state_id + ", city_id=" + city_id
				+ ", status=" + status + "]";
	}
	
	
	
}
