package com.anchor.model;

public class DatabaseModel {

	int id,parentId;
	String name;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DatabaseModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DatabaseModel(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	@Override
	public String toString() {
		return "DatabaseModel [id=" + id + ", name=" + name + "]";
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
	
	
	
}
