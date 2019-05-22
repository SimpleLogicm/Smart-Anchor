package com.anchor.model;

import com.google.gson.annotations.SerializedName;


public class LaunchesItem {

	@SerializedName("date")
	private String date;

	@SerializedName("large")
	private String large;

	@SerializedName("name")
	private String name;

	@SerializedName("type")
	private String type;



	public LaunchesItem(LaunchesItem LaunchesItem){
		this.date = LaunchesItem.date;
		this.large = LaunchesItem.large;
		this.name = LaunchesItem.name;
		this.type = LaunchesItem.type;


	}

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setLarge(String large){
		this.large = large;
	}

	public String getLarge(){
		return large;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	@Override
 	public String toString(){
		return 
			"LaunchesItem{" + 
			"date = '" + date + '\'' + 
			",large = '" + large + '\'' + 
			",name = '" + name + '\'' + 
			",type = '" + type + '\'' + 
			"}";
		}
}