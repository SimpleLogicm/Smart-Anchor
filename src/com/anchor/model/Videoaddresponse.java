package com.anchor.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Videoaddresponse {

	@SerializedName("ads")
	private List<AdsItem> ads;

	public void setAds(List<AdsItem> ads){
		this.ads = ads;
	}

	public List<AdsItem> getAds(){
		return ads;
	}

	@Override
 	public String toString(){
		return 
			"Videoaddresponse{" + 
			"ads = '" + ads + '\'' + 
			"}";
		}
}