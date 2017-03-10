package com.anchor.model;

public class Reason {

	int reason_Id;
	String reason_desc,reason_status,reason_code;
	public Reason() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reason(int reason_Id, String reason_desc, String reason_status,
			String reason_code) {
		super();
		this.reason_Id = reason_Id;
		this.reason_desc = reason_desc;
		this.reason_status = reason_status;
		this.reason_code = reason_code;
	}
	public int getReason_Id() {
		return reason_Id;
	}
	public void setReason_Id(int reason_Id) {
		this.reason_Id = reason_Id;
	}
	public String getReason_desc() {
		return reason_desc;
	}
	public void setReason_desc(String reason_desc) {
		this.reason_desc = reason_desc;
	}
	public String getReason_status() {
		return reason_status;
	}
	public void setReason_status(String reason_status) {
		this.reason_status = reason_status;
	}
	public String getReason_code() {
		return reason_code;
	}
	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}
	@Override
	public String toString() {
		return "Reason [reason_Id=" + reason_Id + ", reason_desc="
				+ reason_desc + ", reason_status=" + reason_status
				+ ", reason_code=" + reason_code + "]";
	}
	
	
}
