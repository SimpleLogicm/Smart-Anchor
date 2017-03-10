package com.anchor.model;

public class OrderItem {
	String   productSpec, order_id;

    int order_item_id,categoryID,productID,schemeID,productQuantity,productDeleiveryQuantity;
    
    float productrp, productmrp,product_total_price;

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItem(String productSpec, String order_id, int order_item_id,
			int categoryID, int productID, int schemeID, int productQuantity,
			int productDeleiveryQuantity, float productrp, float productmrp,
			float product_total_price) {
		super();
		this.productSpec = productSpec;
		this.order_id = order_id;
		this.order_item_id = order_item_id;
		this.categoryID = categoryID;
		this.productID = productID;
		this.schemeID = schemeID;
		this.productQuantity = productQuantity;
		this.productDeleiveryQuantity = productDeleiveryQuantity;
		this.productrp = productrp;
		this.productmrp = productmrp;
		this.product_total_price = product_total_price;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public int getOrder_item_id() {
		return order_item_id;
	}

	public void setOrder_item_id(int order_item_id) {
		this.order_item_id = order_item_id;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getProductID() {
		return productID;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public int getSchemeID() {
		return schemeID;
	}

	public void setSchemeID(int schemeID) {
		this.schemeID = schemeID;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public int getProductDeleiveryQuantity() {
		return productDeleiveryQuantity;
	}

	public void setProductDeleiveryQuantity(int productDeleiveryQuantity) {
		this.productDeleiveryQuantity = productDeleiveryQuantity;
	}

	public float getProductrp() {
		return productrp;
	}

	public void setProductrp(float productrp) {
		this.productrp = productrp;
	}

	public float getProductmrp() {
		return productmrp;
	}

	public void setProductmrp(float productmrp) {
		this.productmrp = productmrp;
	}

	public float getProduct_total_price() {
		return product_total_price;
	}

	public void setProduct_total_price(float product_total_price) {
		this.product_total_price = product_total_price;
	}
    
    
}
