package com.anchor.model;

public class Order {

	String order_id,lat,longitude,SignaturePath,date,retailer_code;
	int userID,cityID,beatID,retailerID,sync,distID;
	float totalPrice;
	
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}


	





	public Order(String order_id, String lat, String longitude,
			String signaturePath, String date, String retailer_code,
			int userID, int cityID, int beatID, int retailerID, int sync,
			int distID, float totalPrice) {
		super();
		this.order_id = order_id;
		this.lat = lat;
		this.longitude = longitude;
		SignaturePath = signaturePath;
		this.date = date;
		this.retailer_code = retailer_code;
		this.userID = userID;
		this.cityID = cityID;
		this.beatID = beatID;
		this.retailerID = retailerID;
		this.sync = sync;
		this.distID = distID;
		this.totalPrice = totalPrice;
	}








	public String getOrder_id() {
		return order_id;
	}


	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public String getSignaturePath() {
		return SignaturePath;
	}


	public void setSignaturePath(String signaturePath) {
		SignaturePath = signaturePath;
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


	public int getSync() {
		return sync;
	}


	public void setSync(int sync) {
		this.sync = sync;
	}


	public float getTotalPrice() {
		return totalPrice;
	}


	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}


	public int getDistID() {
		return distID;
	}


	public void setDistID(int distID) {
		this.distID = distID;
	}








	public String getRetailer_code() {
		return retailer_code;
	}








	public void setRetailer_code(String retailer_code) {
		this.retailer_code = retailer_code;
	}








	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", lat=" + lat + ", longitude="
				+ longitude + ", SignaturePath=" + SignaturePath + ", date="
				+ date + ", retailer_code=" + retailer_code + ", userID="
				+ userID + ", cityID=" + cityID + ", beatID=" + beatID
				+ ", retailerID=" + retailerID + ", sync=" + sync + ", distID="
				+ distID + ", totalPrice=" + totalPrice + "]";
	}


	

	
	
	
	
	
}
