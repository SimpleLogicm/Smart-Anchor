package com.anchor.model;

public class DatabaseProductModel {

	int id,categoryId,schemeId;
	float rp,mrp;
	String productDesc,productSpec,productCode,productStatus;
	String product_unit;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public int getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(int schemeId) {
		this.schemeId = schemeId;
	}
	public float getRp() {
		return rp;
	}
	public void setRp(float rp) {
		this.rp = rp;
	}
	public float getMrp() {
		return mrp;
	}
	public void setMrp(float mrp) {
		this.mrp = mrp;
	}
	public DatabaseProductModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getProductSpec() {
		return productSpec;
	}
	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}
	public String getProduct_unit() {
		return product_unit;
	}
	public void setProduct_unit(String product_unit) {
		this.product_unit = product_unit;
	}
	
	
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	
	
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	
	
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public DatabaseProductModel(int id, int categoryId, int schemeId, float rp,
			float mrp, String productDesc, String productSpec, String product_unit,String productCode,String productStatus) {
		super();
		this.id = id;
		this.categoryId = categoryId;
		this.schemeId = schemeId;
		this.rp = rp;
		this.mrp = mrp;
		this.productDesc = productDesc;
		this.productSpec = productSpec;
		this.product_unit = product_unit;
		this.productCode=productCode;
		this.productStatus=productStatus;
	}
	public DatabaseProductModel(String string) {
		// TODO Auto-generated constructor stub
		
		this.productDesc = productDesc;
	}
	/*@Override
	public String toString() {
		return productDesc;
	}*/
	@Override
	public String toString() {
		return "DatabaseProductModel [id=" + id + ", categoryId=" + categoryId
				+ ", schemeId=" + schemeId + ", rp=" + rp + ", mrp=" + mrp
				+ ", productDesc=" + productDesc + ", productSpec="
				+ productSpec + ", productCode=" + productCode
				+ ", productStatus=" + productStatus + ", product_unit="
				+ product_unit + "]";
	}
	
	
	
	
	
	
}
