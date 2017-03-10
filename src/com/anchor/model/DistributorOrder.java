package com.anchor.model;

public class DistributorOrder {

	
	String OrderID,category,product,productSpec,delQuantity,retailerName,retailerAddress;

	public DistributorOrder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DistributorOrder(String orderID, String category, String product,
			String productSpec, String delQuantity, String retailerName,
			String retailerAddress) {
		super();
		OrderID = orderID;
		this.category = category;
		this.product = product;
		this.productSpec = productSpec;
		this.delQuantity = delQuantity;
		this.retailerName = retailerName;
		this.retailerAddress = retailerAddress;
	}

	public String getOrderID() {
		return OrderID;
	}

	public void setOrderID(String orderID) {
		OrderID = orderID;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public String getDelQuantity() {
		return delQuantity;
	}

	public void setDelQuantity(String delQuantity) {
		this.delQuantity = delQuantity;
	}

	public String getRetailerName() {
		return retailerName;
	}

	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}

	public String getRetailerAddress() {
		return retailerAddress;
	}

	public void setRetailerAddress(String retailerAddress) {
		this.retailerAddress = retailerAddress;
	}

	@Override
	public String toString() {
		return "DistributorOrder [OrderID=" + OrderID + ", category="
				+ category + ", product=" + product + ", productSpec="
				+ productSpec + ", delQuantity=" + delQuantity
				+ ", retailerName=" + retailerName + ", retailerAddress="
				+ retailerAddress + "]";
	}
	
	
	
}
