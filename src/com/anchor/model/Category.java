package com.anchor.model;

public class Category {

	int id,parentid;
	String code,desc,status;
	
	
	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Category(int id, int parentid, String code, String desc,
			String status) {
		super();
		this.id = id;
		this.parentid = parentid;
		this.code = code;
		this.desc = desc;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParentid() {
		return parentid;
	}
	public void setParentid(int parentid) {
		this.parentid = parentid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	@Override
	public String toString() {
		return "Category [id=" + id + ", parentid=" + parentid + ", code="
				+ code + ", desc=" + desc + ", status=" + status + "]";
	}
	
	
	
	
}
