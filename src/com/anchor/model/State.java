package com.anchor.model;

public class State {

	
	String desc,id,status,state_code;
	public State() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public State(String desc, String id, String status, String state_code) {
		super();
		this.desc = desc;
		this.id = id;
		this.status = status;
		this.state_code = state_code;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	


	public String getState_code() {
		return state_code;
	}



	public void setState_code(String state_code) {
		this.state_code = state_code;
	}



	@Override
	public String toString() {
		return "State [desc=" + desc + ", id=" + id + ", status=" + status
				+ ", state_code=" + state_code + "]";
	}

	
	
	
	
}
