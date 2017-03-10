package com.anchor.model;

public class Distributor_mine {

	String DistributorName;
	int DistributorId;

	public Distributor_mine(String distributorName, int distributorId) {
		super();
		DistributorName = distributorName;
		DistributorId = distributorId;
	}

	public String getDistributorName() {
		return DistributorName;
	}

	public void setDistributorName(String distributorName) {
		DistributorName = distributorName;
	}

	public int getDistributorId() {
		return DistributorId;
	}

	public void setDistributorId(int distributorId) {
		DistributorId = distributorId;
	}

}
