package com.anchor.model;

public class Products_Mine {

	String ProductName;
	int ProductId;
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public int getProductId() {
		return ProductId;
	}
	public void setProductId(int productId) {
		ProductId = productId;
	}
	public Products_Mine(String productName, int productId) {
		super();
		ProductName = productName;
		ProductId = productId;
	}
	
	
	
}
