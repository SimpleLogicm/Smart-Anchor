package com.anchor.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

	String category, productName, productSpec, productQuantity,
			productDeleiveryQuantity, productScheme, productrp, productmrp,
			producttotalPrice,subcategory;
	
	int categoryID,productID,schemeID;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Product(String category,String subcategory, String productName, String productSpec,
			String productQuantity, String productDeleiveryQuantity,
			String productScheme, String productrp, String productmrp,
			String producttotalPrice) {
		super();
		this.category = category;
		this.subcategory = subcategory;
		this.productName = productName;
		this.productSpec = productSpec;
		this.productQuantity = productQuantity;
		this.productDeleiveryQuantity = productDeleiveryQuantity;
		this.productScheme = productScheme;
		this.productrp = productrp;
		this.productmrp = productmrp;
		this.producttotalPrice = producttotalPrice;
	}

	
	



	@Override
	public String toString() {
		return "Product [category=" + category + ", productName=" + productName
				+ ", productSpec=" + productSpec + ", productQuantity="
				+ productQuantity + ", productDeleiveryQuantity="
				+ productDeleiveryQuantity + ", productScheme=" + productScheme
				+ ", productrp=" + productrp + ", productmrp=" + productmrp
				+ ", producttotalPrice=" + producttotalPrice + ", categoryID="
				+ categoryID + ", productID=" + productID + ", schemeID="
				+ schemeID + "]";
	}







	public String getSubcategory() {
		return subcategory;
	}


	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}


	public Product(Parcel source) {
		category = source.readString();
		productName = source.readString();
		productSpec = source.readString();
        
		productQuantity = source.readString();
		productDeleiveryQuantity = source.readString();
		productScheme =source.readString();
        
		productrp = source.readString();
		productmrp =source.readString();
		producttotalPrice =source.readString();
		
		categoryID = source.readInt();
		productID = source.readInt();
		schemeID = source.readInt();
        
       
    }


	
	public Product(String category, String productName, String productSpec,
			String productQuantity, String productDeleiveryQuantity,
			String productScheme, String productrp, String productmrp,
			String producttotalPrice, int categoryID, int productID,
			int schemeID) {
		super();
		this.category = category;
		this.productName = productName;
		this.productSpec = productSpec;
		this.productQuantity = productQuantity;
		this.productDeleiveryQuantity = productDeleiveryQuantity;
		this.productScheme = productScheme;
		this.productrp = productrp;
		this.productmrp = productmrp;
		this.producttotalPrice = producttotalPrice;
		this.categoryID = categoryID;
		this.productID = productID;
		this.schemeID = schemeID;
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


	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public String getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(String productQuantity) {
		this.productQuantity = productQuantity;
	}

	public String getProductDeleiveryQuantity() {
		return productDeleiveryQuantity;
	}

	public void setProductDeleiveryQuantity(String productDeleiveryQuantity) {
		this.productDeleiveryQuantity = productDeleiveryQuantity;
	}

	public String getProductScheme() {
		return productScheme;
	}

	public void setProductScheme(String productScheme) {
		this.productScheme = productScheme;
	}

	public String getProductrp() {
		return productrp;
	}

	public void setProductrp(String productrp) {
		this.productrp = productrp;
	}

	public String getProductmrp() {
		return productmrp;
	}

	public void setProductmrp(String productmrp) {
		this.productmrp = productmrp;
	}

	public String getProducttotalPrice() {
		return producttotalPrice;
	}

	public void setProducttotalPrice(String producttotalPrice) {
		this.producttotalPrice = producttotalPrice;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return this.hashCode();
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {

		dest.writeString(category);
		dest.writeString(productName);
		dest.writeString(productSpec);
		dest.writeString(productQuantity);
		dest.writeString(productDeleiveryQuantity);
		dest.writeString(productScheme);
		dest.writeString(productrp);
		dest.writeString(productmrp);
		dest.writeString(producttotalPrice);
		dest.writeInt(categoryID);
		dest.writeInt(productID);
		dest.writeInt(schemeID);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
		public Product createFromParcel(Parcel in) {
			return new Product(in);
		}

		public Product[] newArray(int size) {
			
			Product [] p=new Product[size];
			return p;
		}
	};

}
