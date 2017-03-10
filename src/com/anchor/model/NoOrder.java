package com.anchor.model;

public class NoOrder {
	String no_order_id,reason_other,date,retailer_code;
	int userID,cityID,beatID,retailerID,reasonID;
	public NoOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NoOrder(String no_order_id, String reason_other, String date,
			String retailer_code, int userID, int cityID, int beatID,
			int retailerID, int reasonID) {
		super();
		this.no_order_id = no_order_id;
		this.reason_other = reason_other;
		this.date = date;
		this.retailer_code = retailer_code;
		this.userID = userID;
		this.cityID = cityID;
		this.beatID = beatID;
		this.retailerID = retailerID;
		this.reasonID = reasonID;
	}

	public String getNo_order_id() {
		return no_order_id;
	}
	public void setNo_order_id(String no_order_id) {
		this.no_order_id = no_order_id;
	}
	public String getReason_other() {
		return reason_other;
	}
	public void setReason_other(String reason_other) {
		this.reason_other = reason_other;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getCityID() {
		return cityID;
	}
	public void setCityID(int cityID) {
		this.cityID = cityID;
	}
	public int getBeatID() {
		return beatID;
	}
	public void setBeatID(int beatID) {
		this.beatID = beatID;
	}
	public int getRetailerID() {
		return retailerID;
	}
	public void setRetailerID(int retailerID) {
		this.retailerID = retailerID;
	}
	public int getReasonID() {
		return reasonID;
	}
	public void setReasonID(int reasonID) {
		this.reasonID = reasonID;
	}

	@Override
	public String toString() {
		return "NoOrder [no_order_id=" + no_order_id + ", reason_other="
				+ reason_other + ", date=" + date + ", retailer_code="
				+ retailer_code + ", userID=" + userID + ", cityID=" + cityID
				+ ", beatID=" + beatID + ", retailerID=" + retailerID
				+ ", reasonID=" + reasonID + "]";
	}

	public String getRetailer_code() {
		return retailer_code;
	}

	public void setRetailer_code(String retailer_code) {
		this.retailer_code = retailer_code;
	}
	
	
	
	
}
